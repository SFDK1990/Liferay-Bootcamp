package com.liferay.training.gradebook.web.internal.security.permission.resource;

 import com.liferay.portal.kernel.security.permission.PermissionChecker;
 import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
 import com.liferay.training.gradebook.constants.GradebookConstants;

 import org.osgi.service.component.annotations.Component;
 import org.osgi.service.component.annotations.Reference;

 /**
  * @author liferay
  */
 @Component(
     immediate = true
 )
 public class AssignmentTopLevelPermission {

     public static boolean contains(
         PermissionChecker permissionChecker, long groupId, String actionId) {

         return _portletResourcePermission.contains(
             permissionChecker, groupId, actionId);
     }

     @Reference(
         target = "(resource.name=" + GradebookConstants.RESOURCE_NAME + ")",
         unbind = "-"
     )
     protected void setPortletResourcePermission(
         PortletResourcePermission portletResourcePermission) {

         _portletResourcePermission = portletResourcePermission;
     }

     private static PortletResourcePermission _portletResourcePermission;

 }