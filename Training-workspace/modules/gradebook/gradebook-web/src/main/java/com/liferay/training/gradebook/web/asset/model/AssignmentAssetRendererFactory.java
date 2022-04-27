package com.liferay.training.gradebook.web.asset.model;

 import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
 import com.liferay.asset.kernel.model.AssetRenderer;
 import com.liferay.asset.kernel.model.AssetRendererFactory;
 import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
 import com.liferay.portal.kernel.exception.PortalException;
 import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
 import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
 import com.liferay.portal.kernel.portlet.LiferayPortletURL;
 import com.liferay.portal.kernel.portlet.PortletURLFactory;
 import com.liferay.portal.kernel.security.permission.ActionKeys;
 import com.liferay.portal.kernel.security.permission.PermissionChecker;
 import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
 import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
 import com.liferay.portal.kernel.util.Portal;
 import com.liferay.training.gradebook.constants.GradebookConstants;
 import com.liferay.training.gradebook.model.Assignment;
 import com.liferay.training.gradebook.service.AssignmentLocalService;
 import com.liferay.training.gradebook.web.constants.GradebookPortletKeys;
 import com.liferay.training.gradebook.web.constants.MVCCommandNames;

 import javax.portlet.PortletRequest;
 import javax.portlet.PortletURL;
 import javax.portlet.WindowState;
 import javax.portlet.WindowStateException;
 import javax.servlet.ServletContext;

 import org.osgi.service.component.annotations.Component;
 import org.osgi.service.component.annotations.Reference;

 /**
  * Asset renderer factory component for assignments.
  *
  * @author liferay
  */
 @Component(
     immediate = true,
     property = "javax.portlet.name=" + GradebookPortletKeys.GRADEBOOK,
     service = AssetRendererFactory.class
 )
 public class AssignmentAssetRendererFactory
     extends BaseAssetRendererFactory<Assignment> {

     public static final String CLASS_NAME = Assignment.class.getName();

     public static final String TYPE = "assignment";

     public AssignmentAssetRendererFactory() {
         setClassName(CLASS_NAME);
         setLinkable(true);
         setPortletId(GradebookPortletKeys.GRADEBOOK);
         setSearchable(true);
     }

     @Override
     public AssetRenderer<Assignment> getAssetRenderer(long classPK, int type)
         throws PortalException {

         Assignment assignment = _assignmentLocalService.getAssignment(classPK);

         AssignmentAssetRenderer assignmentAssetRenderer =
             new AssignmentAssetRenderer(
                     assignment);

         assignmentAssetRenderer.setAssetDisplayPageFriendlyURLProvider( 
             _assetDisplayPageFriendlyURLProvider);
         assignmentAssetRenderer.setAssetRendererType(type);
         assignmentAssetRenderer.setServletContext(_servletContext);

         return assignmentAssetRenderer;
     }

     @Override
     public String getType() {
         return TYPE;
     }

     @Override
     public PortletURL getURLAdd(
         LiferayPortletRequest liferayPortletRequest,
         LiferayPortletResponse liferayPortletResponse, long classTypeId) {

         PortletURL portletURL = _portal.getControlPanelPortletURL(
             liferayPortletRequest, getGroup(liferayPortletRequest),
             GradebookPortletKeys.GRADEBOOK, 0, 0, PortletRequest.RENDER_PHASE);

         portletURL.setParameter("mvcRenderCommandName", MVCCommandNames.EDIT_ASSIGNMENT);

         return portletURL;
     }

     @Override
     public PortletURL getURLView(
         LiferayPortletResponse liferayPortletResponse,
         WindowState windowState) {

         LiferayPortletURL liferayPortletURL =
             liferayPortletResponse.createLiferayPortletURL(
                 GradebookPortletKeys.GRADEBOOK, PortletRequest.RENDER_PHASE);

         try {
             liferayPortletURL.setWindowState(windowState);
         }
         catch (WindowStateException wse) {
         }

         return liferayPortletURL;
     }    

     @Override
     public boolean hasAddPermission(
             PermissionChecker permissionChecker, long groupId, long classTypeId)
         throws Exception {

         return _portletResourcePermission.contains(
             permissionChecker, groupId, ActionKeys.ADD_ENTRY);
     }

     @Override
     public boolean hasPermission(
             PermissionChecker permissionChecker, long classPK, String actionId)
         throws Exception {

         return _assignmentModelResourcePermission.contains(
             permissionChecker, classPK, actionId);
     }

     @Reference
     private AssetDisplayPageFriendlyURLProvider
         _assetDisplayPageFriendlyURLProvider;

     @Reference
     private AssignmentLocalService _assignmentLocalService;

     @Reference(
         target = "(model.class.name=com.liferay.training.gradebook.model.Assignment)"
     )
     private ModelResourcePermission<Assignment>
         _assignmentModelResourcePermission;

     @Reference
     private Portal _portal;

     @Reference(
         target = "(resource.name=" + GradebookConstants.RESOURCE_NAME + ")"
     )
     private PortletResourcePermission _portletResourcePermission;

     @Reference
     private PortletURLFactory _portletURLFactory;

     @Reference(
         target = "(osgi.web.symbolicname=com.liferay.training.gradebook.web)"
     )
     private ServletContext _servletContext;

 }