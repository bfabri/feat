package br.pucrio.inf.les.feat.core.domainmodel;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

public interface IStyledLabel {
	static final ISharedImages SHARED_IMAGES = PlatformUI.getWorkbench().getSharedImages();
	StyledString getStyledLabel();
	Image getImage();
}
