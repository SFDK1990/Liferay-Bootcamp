package com.liferay.training.gradebook.internal.security.permission.resource.definition;

 import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
 import com.liferay.portal.kernel.exception.PortalException;
 import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
 import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
 import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
 import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;
 import com.liferay.portal.kernel.security.permission.resource.WorkflowedModelPermissionLogic;
 import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;
 import com.liferay.portal.kernel.service.GroupLocalService;
 import com.liferay.portal.kernel.workflow.permission.WorkflowPermission;
 import com.liferay.training.gradebook.constants.GradebookConstants;
 import com.liferay.training.gradebook.model.Assignment;
 import com.liferay.training.gradebook.service.AssignmentLocalService;

 import java.util.function.Consumer;

 import org.osgi.service.component.annotations.Component;
 import org.osgi.service.component.annotations.Reference;

 /**
  * @author liferay
  */
 @Component(
     immediate = true, 
     service = ModelResourcePermissionDefinition.class
 )
 public class AssignmentModelResourcePermissionDefinition
     implements ModelResourcePermissionDefinition<Assignment> {

     @Override
     public Assignment getModel(long assignmentId)
         throws PortalException {

         return _assignmentLocalService.getAssignment(assignmentId);
     }

     @Override
     public Class<Assignment> getModelClass() {

         return Assignment.class;
     }

     @Override
     public PortletResourcePermission getPortletResourcePermission() {

         return _portletResourcePermission;
     }

     @Override
     public long getPrimaryKey(Assignment assignment) {

         return assignment.getAssignmentId();
     }

     @Override
     public void registerModelResourcePermissionLogics(
         ModelResourcePermission<Assignment> modelResourcePermission,
         Consumer<ModelResourcePermissionLogic<Assignment>> modelResourcePermissionLogicConsumer) {

         modelResourcePermissionLogicConsumer.accept(
             new StagedModelPermissionLogic<>(
                 _stagingPermission,
                 "com_liferay_training_gradebook_web_portlet_GradebookPortlet",
                 Assignment::getAssignmentId));

         // Only enable if you use (optional) workflow support

         //    modelResourcePermissionLogicConsumer.accept(
         //        new WorkflowedModelPermissionLogic<>(
         //            _workflowPermission, modelResourcePermission,
         //            _groupLocalService, Assignment::getAssignmentId));
     }

     @Reference
     private AssignmentLocalService _assignmentLocalService;

     @Reference
     private GroupLocalService _groupLocalService;

     @Reference(target = "(resource.name=" + GradebookConstants.RESOURCE_NAME + ")")
     private PortletResourcePermission _portletResourcePermission;

     @Reference
     private StagingPermission _stagingPermission;

     @Reference
     private WorkflowPermission _workflowPermission;
 }