package br.pucrio.inf.les.feat.parser;

import static dk.au.cs.java.compiler.Util.showPhaseProgress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import dk.au.cs.java.compiler.ErrorType;
import dk.au.cs.java.compiler.Errors;
import dk.au.cs.java.compiler.Flags;
import dk.au.cs.java.compiler.Main;
import dk.au.cs.java.compiler.SourceError;
import dk.au.cs.java.compiler.analysis.DepthFirstAdapter;
import dk.au.cs.java.compiler.cfg.gen.CFGGenerator;
import dk.au.cs.java.compiler.check.DisambiguationCheck;
import dk.au.cs.java.compiler.check.EnvironmentsCheck;
import dk.au.cs.java.compiler.check.HierarchyCheck;
import dk.au.cs.java.compiler.check.TypeCheckingCheck;
import dk.au.cs.java.compiler.check.TypeLinkingCheck;
import dk.au.cs.java.compiler.check.WeedingCheck;
import dk.au.cs.java.compiler.ifdef.IfDefBDDAssigner;
import dk.au.cs.java.compiler.ifdef.IfDefUtil;
import dk.au.cs.java.compiler.ifdef.IfDefVarSet;
import dk.au.cs.java.compiler.ifdef.SharedSimultaneousAnalysis;
import dk.au.cs.java.compiler.lexer.Lexer;
import dk.au.cs.java.compiler.lexer.LexerException;
import dk.au.cs.java.compiler.node.ACompilationUnit;
import dk.au.cs.java.compiler.node.AIfdefStm;
import dk.au.cs.java.compiler.node.AProgram;
import dk.au.cs.java.compiler.node.Start;
import dk.au.cs.java.compiler.node.TIdentifier;
import dk.au.cs.java.compiler.parser.Parser;
import dk.au.cs.java.compiler.parser.ParserException;
import dk.au.cs.java.compiler.phases.Disambiguation;
import dk.au.cs.java.compiler.phases.Environments;
import dk.au.cs.java.compiler.phases.Hierarchy;
import dk.au.cs.java.compiler.phases.Reachability;
import dk.au.cs.java.compiler.phases.Resources;
import dk.au.cs.java.compiler.phases.TargetResolver;
import dk.au.cs.java.compiler.phases.TypeChecking;
import dk.au.cs.java.compiler.phases.TypeLinking;
import dk.au.cs.java.compiler.phases.Weeding;
import dk.au.cs.java.compiler.phases.XACTDesugaring;
import dk.au.cs.java.compiler.type.environment.ClassEnvironment;
import dk.brics.util.file.WildcardExpander;

public class FeatParser {

	private static final String javaFormat = "%1$s%2$c**%2$c*.java";
	private static final String jarFormat = "%1$s%2$c**%2$c*.jar";
	
	private static AProgram rootNode;
	
	public static void main(String[] args) {
		File project = new File("../JCalc");
		if (project.exists()) {
			parse(project);
		}
	}
	
	public static void parse(File project) {
		if (!project.exists() || !project.isDirectory()) {
			throw new IllegalArgumentException("Method expect a existing directory.");
		}
		
		Main.resetCompiler();
		File ifdefSpecFile = null;
		List<File> javaFiles = new ArrayList<>();
		List<File> jarFiles = new ArrayList<>();
		for (File file : project.listFiles()) {
			if (file.isDirectory()) {
				if (file.getName().equals("src")) {
					String filepath = String.format(javaFormat, file.getPath(), File.separatorChar);
					List<File> expandWildcards = WildcardExpander.expandWildcards(filepath);
					javaFiles.addAll(expandWildcards);
				}
				else if (file.getName().equals("lib")) {
					String filepath = String.format(jarFormat, file.getPath(), File.separatorChar);
					List<File> expandWildcards = WildcardExpander.expandWildcards(filepath);
					jarFiles.addAll(expandWildcards);
				}
			}
			else if (file.getName().equals("ifdef.txt")) {
				ifdefSpecFile = file;
			}
		}
		
		String classpath = generateClassPath(jarFiles);
		ClassEnvironment.init(classpath, false);

		EnumSet<Flags> flags = (EnumSet<Flags>) Main.FLAGS;
		flags.add(Flags.IFDEF);
		SharedSimultaneousAnalysis.useSharedSetStrategy(true);
		IfDefUtil.parseIfDefSpecification(ifdefSpecFile);

		IfDefVarSet.getIfDefBDDFactory();
		
		rootNode = parseProgram(javaFiles);
		
		Main.program = rootNode;

		rootNode.setOptionalInvariant(true);

		Set<IfDefVarSet> computableFeatureSets = Collections.singleton(IfDefVarSet.getAll());
		IfDefVarSet featureSet = computableFeatureSets.iterator().next();
		
		/*
		 * Apply compiler phases to the root node.
		 * 
		 * TODO: Check if some of these can be removed to speed things up.
		 */
		try {
			Errors.check();
			rootNode.apply(new Weeding());
			Errors.check();
			rootNode.apply(new WeedingCheck());
			Errors.check();
			rootNode.apply(new IfDefBDDAssigner(featureSet));
			Errors.check();
			rootNode.apply(new Environments());
			Errors.check();
			rootNode.apply(new EnvironmentsCheck());
			Errors.check();
			rootNode.apply(new TypeLinking());
			Errors.check();
			rootNode.apply(new TypeLinkingCheck());
			Errors.check();
			rootNode.apply(new Hierarchy());
			Errors.check();
			rootNode.apply(new HierarchyCheck());
			Errors.check();
			rootNode.apply(new Disambiguation());
			Errors.check();
			rootNode.apply(new DisambiguationCheck());
			Errors.check();
			rootNode.apply(new TargetResolver());
			Errors.check();
			rootNode.apply(new Reachability(featureSet));
			Errors.check();

			// Un/Comment line below to en/disable constant folding
			// optimization.
			// node.apply(new ConstantFolding());
			// Errors.check();

			rootNode.apply(new TypeChecking());
			Errors.check();
			rootNode.apply(new TypeCheckingCheck());
			Errors.check();
			rootNode.apply(new CFGGenerator());
			Errors.check();

			// FIXME: something goes wrong in the PromotionInference.
			// node.apply(new PromotionInference());
			// Errors.check();

			rootNode.apply(new XACTDesugaring());
			Errors.check();
			rootNode.apply(new Resources());
			Errors.check();
		} catch (SourceError ex) {
			ex.printStackTrace();
			throw new RuntimeException("Compilation error: " + ex.getMessage());
		}
		
		rootNode.apply(new DepthFirstAdapter() {
			
			
			@Override
			public void inAIfdefStm(AIfdefStm node) {
				System.out.println("Entrando na feature " + node.getExp().toString());
				super.inAIfdefStm(node);
			}
			
			@Override
			public void outAIfdefStm(AIfdefStm node) {
				System.out.println("Saindo da feature " + node.getExp().toString());
				super.outAIfdefStm(node);
			}
		});
	}
	
	private static String generateClassPath(Collection<File> jarFiles) {
		StringBuilder classpath = new StringBuilder();
		for (File file : jarFiles) {
			classpath.append(file.getAbsolutePath() + File.pathSeparator);
		}
		String result = classpath.toString();
		return result == null || result.isEmpty() ? "" : result.substring(0, result.length() - 1);
	}
	
	public static AProgram parseProgram(List<File> sourceFiles) {
		final List<ACompilationUnit> sources = new ArrayList<ACompilationUnit>();

		for (final File file : sourceFiles) {
			try {
				showPhaseProgress();
				FileInputStream fis = new FileInputStream(file);
				Parser parser = new Parser(new Lexer(fis));
				Start startsym = parser.parse();
				fis.close();
				ACompilationUnit compilationUnit = startsym
						.getCompilationUnit();
				compilationUnit.setToken(new TIdentifier(file.getPath(), 0, 0));
				compilationUnit.setFile(file);
				sources.add(compilationUnit);
			} catch (FileNotFoundException e) {
				Errors.errorMessage(ErrorType.FILE_OPEN_ERROR,
						"File " + file.getPath() + " not found");
				Errors.check(); // no use in parsing of not all files can be
								// found
			} catch (LexerException e) {
				Errors.error(ErrorType.LEXER_EXCEPTION, file, e.getLine(),
						e.getPos(), "Syntax error at " + file.getPath() + " "
								+ e.getMessage(), false);
			} catch (ParserException e) {
				Errors.error(ErrorType.PARSER_EXCEPTION, file, e.getToken()
						.getLine(), e.getToken().getPos(), "Syntax error at "
						+ file.getPath() + " " + e.getMessage(), false);
			} catch (IOException e) {
				Errors.errorMessage(ErrorType.ARGUMENT_ERROR, "Error reading file "
						+ file.getPath() + ": " + e.getMessage());
				Errors.check(); // no use in parsing of not all files can be
								// read
			}
		}

		AProgram node = new AProgram(new TIdentifier("AProgram", 0, 0), sources);
		// enables the runtime tree invariant
		node.setOptionalInvariant(true);
		return node;
	}

}
