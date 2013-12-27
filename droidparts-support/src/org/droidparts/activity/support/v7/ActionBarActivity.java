/**
 * Copyright 2013 Alex Yanchenko
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.droidparts.activity.support.v7;

import org.droidparts.Injector;
import org.droidparts.bus.EventBus;
import org.droidparts.contract.Injectable;
import org.droidparts.inner.fragments.SecretFragmentsSupportUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;

public abstract class ActionBarActivity extends
		android.support.v7.app.ActionBarActivity implements Injectable {

	private MenuItem reloadMenuItem;
	private View loadingIndicator;

	private boolean isLoading;

	@Override
	public void onPreInject() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onPreInject();
		Injector.inject(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		EventBus.registerAnnotatedReceiver(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		EventBus.unregisterAnnotatedReceiver(this);
	}

	public final void setActionBarLoadingIndicatorVisible(boolean visible) {
		isLoading = visible;
		if (reloadMenuItem != null) {
			reloadMenuItem.setActionView(visible ? loadingIndicator : null);
		} else {
			super.setSupportProgressBarIndeterminateVisibility(visible);
		}
	}

	public final void setActionBarReloadMenuItem(MenuItem menuItem) {
		this.reloadMenuItem = menuItem;
		if (menuItem != null && loadingIndicator == null) {
			loadingIndicator = SecretFragmentsSupportUtil
					.fragmentActivityBuildLoadingIndicator(this);
		}
		setActionBarLoadingIndicatorVisible(isLoading);
	}

	public void setFragmentVisible(boolean visible, Fragment... fragments) {
		SecretFragmentsSupportUtil.fragmentActivitySetFragmentVisible(this,
				visible, fragments);
	}

}