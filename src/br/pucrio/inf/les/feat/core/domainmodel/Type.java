package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import br.pucrio.inf.les.feat.ui.util.LocationStyle;

public class Type extends Element {
	private FeatType type;

	public Type(String name, String elementPackage, int startLine, FeatType type) {
		super(name, elementPackage, startLine);
		this.type = type;
	}

	@Override
	public Image getImage() {
		Image result;
		switch (type) {
		case CLASS:
			result = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_CLASS);
			break;
		case ENUM:
			result = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_ENUM);
			break;
		default:
			result = JavaUI.getSharedImages().getImage(
					org.eclipse.jdt.ui.ISharedImages.IMG_OBJS_INTERFACE);
			break;
		}
		return result;
	}

	@Override
	public StyledString getStyledLabel() {
		StyledString styledLabel = new StyledString();
		styledLabel.append(super.name);
		styledLabel.append(
				String.format(" [%1s.%2s] ", super.elementPackage, super.name),
				new LocationStyle());
		return styledLabel;
	}
}
