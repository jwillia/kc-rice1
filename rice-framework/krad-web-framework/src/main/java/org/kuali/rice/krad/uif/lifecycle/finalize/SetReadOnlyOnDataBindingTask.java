/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krad.uif.lifecycle.finalize;

import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.component.DataBinding;
import org.kuali.rice.krad.uif.lifecycle.AbstractViewLifecycleTask;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecyclePhase;
import org.kuali.rice.krad.uif.view.ViewModel;

/**
 * Perform custom finalize behavior for the component defined by the helper.
 * 
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class SetReadOnlyOnDataBindingTask extends AbstractViewLifecycleTask {

    /**
     * Constructor.
     * 
     * @param phase The finalize phase for the component.
     */
    public SetReadOnlyOnDataBindingTask(ViewLifecyclePhase phase) {
        super(phase);
    }

    /**
     * @see org.kuali.rice.krad.uif.lifecycle.AbstractViewLifecycleTask#performLifecycleTask()
     */
    @Override
    protected void performLifecycleTask() {
        // implement readonly request overrides
        Component component = getPhase().getComponent();
        ViewModel viewModel = (ViewModel) getPhase().getModel();
        if ((component instanceof DataBinding)
                && ViewLifecycle.getView().isSupportsRequestOverrideOfReadOnlyFields()
                && !viewModel.getReadOnlyFieldsList().isEmpty()) {
            String propertyName = ((DataBinding) component).getPropertyName();
            if (viewModel.getReadOnlyFieldsList().contains(propertyName)) {
                component.setReadOnly(true);
            }
        }
    }

}
