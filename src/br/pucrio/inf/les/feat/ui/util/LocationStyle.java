package br.pucrio.inf.les.feat.ui.util;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

public class LocationStyle extends Styler {
	@Override
	public void applyStyles(TextStyle textStyle) {
		textStyle.foreground = Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_GREEN);
	}
}
