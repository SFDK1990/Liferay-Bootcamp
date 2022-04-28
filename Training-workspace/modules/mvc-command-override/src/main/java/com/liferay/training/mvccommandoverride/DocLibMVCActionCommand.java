package com.liferay.training.mvccommandoverride;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author dasencio
 */
@Component(immediate = true, property = { "javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY, "mvc.command.name=/document_library/edit_folder",
		"service.ranking:Integer=100" }, service = MVCActionCommand.class)
public class DocLibMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		System.out.println("CMD=" + cmd);

		return _mvcActionCommand.processAction(actionRequest, actionResponse);
	}

	@Reference(target = "(component.name=com.liferay.document.library.web.internal.portlet.action.EditFolderMVCActionCommand)")
	private MVCActionCommand _mvcActionCommand;

}