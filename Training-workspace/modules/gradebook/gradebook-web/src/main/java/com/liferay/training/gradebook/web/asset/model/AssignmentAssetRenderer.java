package com.liferay.training.gradebook.web.asset.model;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.asset.util.AssetHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.training.gradebook.model.Assignment;
import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
import com.liferay.training.gradebook.web.constants.MVCCommandNames;
import com.liferay.training.gradebook.web.internal.security.permission.resource.AssignmentPermission;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Asset renderer for assignments.
 *
 * @author liferay
 */
public class AssignmentAssetRenderer extends BaseJSPAssetRenderer<Assignment> {

	public AssignmentAssetRenderer(Assignment assignment) {

		_assignment = assignment;
	}

	@Override
	public Assignment getAssetObject() {
		return _assignment;
	}

	@Override
	public String getClassName() {
		return Assignment.class.getName();
	}

	@Override
	public long getClassPK() {
		return _assignment.getAssignmentId();
	}

	@Override
	public long getGroupId() {
		return _assignment.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) || template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _assignment.getStatus();
	}

	@Override
	public String getSummary(PortletRequest portletRequest, PortletResponse portletResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay) portletRequest.getAttribute(WebKeys.THEME_DISPLAY);

		int abstractLength = AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH;

		String summary = HtmlUtil
				.stripHtml(StringUtil.shorten(_assignment.getDescription(themeDisplay.getLocale()), abstractLength));

		return summary;
	}

	@Override
	public String getTitle(Locale locale) {
		return _assignment.getTitle(locale);
	}

	@Override
	public PortletURL getURLEdit(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse) throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(_assignment.getGroupId());

		if (group.isCompany()) {
			ThemeDisplay themeDisplay = (ThemeDisplay) liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);

			group = themeDisplay.getScopeGroup();
		}

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(liferayPortletRequest, group,
				GradebookPortletKeys.GRADEBOOK, 0, 0, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.EDIT_ASSIGNMENT);
		portletURL.setParameter("assignmentId", String.valueOf(_assignment.getAssignmentId()));
		portletURL.setParameter("showback", Boolean.FALSE.toString());

		return portletURL;
	}

	@Override
	public String getURLView(LiferayPortletResponse liferayPortletResponse, WindowState windowState) throws Exception {

		return super.getURLView(liferayPortletResponse, windowState);
	}

	@Override
	public String getURLViewInContext(LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, String noSuchEntryRedirect) throws Exception {

		if (_assetDisplayPageFriendlyURLProvider != null) {
			ThemeDisplay themeDisplay = (ThemeDisplay) liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String friendlyURL = _assetDisplayPageFriendlyURLProvider.getFriendlyURL(getClassName(), getClassPK(),
					themeDisplay);

			if (Validator.isNotNull(friendlyURL)) {
				return friendlyURL;
			}
		}

		try {
			long plid = PortalUtil.getPlidFromPortletId(_assignment.getGroupId(), GradebookPortletKeys.GRADEBOOK);

			PortletURL portletURL;

			if (plid == LayoutConstants.DEFAULT_PLID) {
				portletURL = liferayPortletResponse.createLiferayPortletURL(getControlPanelPlid(liferayPortletRequest),
						GradebookPortletKeys.GRADEBOOK, PortletRequest.RENDER_PHASE);
			} else {
				portletURL = PortletURLFactoryUtil.getPortletURLFactory().create(liferayPortletRequest,
						GradebookPortletKeys.GRADEBOOK, plid, PortletRequest.RENDER_PHASE);
			}

			portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.VIEW_ASSIGNMENT);
			portletURL.setParameter("assignmentId", String.valueOf(_assignment.getAssignmentId()));

			String currentUrl = PortalUtil.getCurrentURL(liferayPortletRequest);

			portletURL.setParameter("redirect", currentUrl);

			return portletURL.toString();
		} catch (PortalException pe) {
		} catch (SystemException se) {
		}

		return null;
	}

	@Override
	public long getUserId() {
		return _assignment.getUserId();
	}

	@Override
	public String getUserName() {
		return _assignment.getUserName();
	}

	@Override
	public String getUuid() {
		return _assignment.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException {

		return AssignmentPermission.contains(permissionChecker, _assignment, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker) throws PortalException {

		return AssignmentPermission.contains(permissionChecker, _assignment, ActionKeys.VIEW);
	}

	@Override
	public boolean include(HttpServletRequest request, HttpServletResponse response, String template) throws Exception {

		request.setAttribute("assignment", _assignment);

		return super.include(request, response, template);
	}

	public void setAssetDisplayPageFriendlyURLProvider(
			AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider) {

		_assetDisplayPageFriendlyURLProvider = assetDisplayPageFriendlyURLProvider;
	}

	private AssetDisplayPageFriendlyURLProvider _assetDisplayPageFriendlyURLProvider;

	private Assignment _assignment;
}