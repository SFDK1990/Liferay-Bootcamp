package com.liferay.training.expando;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.osgi.service.component.annotations.Component;

/**
 * @author dasencio
 */
@Component(
	immediate = true,
	property = {
			"key=application.startup.events"
	},
	service = LifecycleAction.class
)
public class UserExpandoStartupAction implements LifecycleAction {

	@Override
	public void processLifecycleEvent(LifecycleEvent lifecycleEvent)
		throws ActionException {

		System.out.println("Expando exercise executing.");

		long companyId = CompanyThreadLocal.getCompanyId();

		// Try to get the expandoBridge for the User class and add a custom attribute.
		// By setting the secure option to false, we instruct the expandoBridge to
		// use Expando's unchecked local services instead of the permission-checked
		// remote services.

		boolean secure = false;

		try {

			ExpandoBridge expandoBridge = ExpandoBridgeFactoryUtil
					.getExpandoBridge(companyId, User.class.getName());
			expandoBridge.addAttribute("linkedin_profile_id", secure);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}