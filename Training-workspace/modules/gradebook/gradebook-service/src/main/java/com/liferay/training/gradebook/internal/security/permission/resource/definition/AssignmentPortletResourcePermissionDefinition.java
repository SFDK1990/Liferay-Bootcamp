package com.liferay.training.gradebook.internal.security.permission.resource.definition;

 import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
 import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionLogic;
 import com.liferay.portal.kernel.security.permission.resource.StagedPortletPermissionLogic;
 import com.liferay.portal.kernel.security.permission.resource.definition.PortletResourcePermissionDefinition;
 import com.liferay.training.gradebook.constants.GradebookConstants;

 import org.osgi.service.component.annotations.Component;
 import org.osgi.service.component.annotations.Reference;

 /**
  * @author liferay
  */
 @Component(
     immediate = true, 
     service = PortletResourcePermissionDefinition.class
 )
 public class AssignmentPortletResourcePermissionDefinition
     implements PortletResourcePermissionDefinition {

     @Override
     public PortletResourcePermissionLogic[] getPortletResourcePermissionLogics() {

         return new PortletResourcePermissionLogic[] {
             new StagedPortletPermissionLogic(
                 _stagingPermission,
                 "com_liferay_training_gradebook_web_portlet_GradebookPortlet")
         };
     }

     @Override
     public String getResourceName() {

         return GradebookConstants.RESOURCE_NAME;
     }

     @Reference
     private StagingPermission _stagingPermission;

 }