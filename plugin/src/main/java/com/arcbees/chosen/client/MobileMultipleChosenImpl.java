/**
 * Copyright 2014 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.chosen.client;

import com.arcbees.chosen.client.SelectParser.OptionItem;
import com.arcbees.chosen.client.event.ChosenChangeEvent;
import com.arcbees.chosen.client.event.MaxSelectedEvent;
import com.google.gwt.query.client.GQuery;

public class MobileMultipleChosenImpl extends AbstractMobileChosenImpl {
    @Override
    public boolean isMultiple() {
        return true;
    }

    @Override
    protected void resultsBuild(boolean init, String defaultText, boolean customFilter) {
        if (choices > 0) {
            choices = 0;
        }
        super.resultsBuild(init, defaultText, customFilter);
    }

    @Override
    protected void addChoice(OptionItem item) {
        if (maxSelectedOptionsReached()) {
            fireEvent(new MaxSelectedEvent(this));
        } else {
            String optionSelector = "#" + getContainerId() + "_o_" + item.getArrayIndex();
            choices++;

            getSearchResults().find(optionSelector).addClass(getCss().resultSelected());

            String selectedText;
            if (choices > 1) {
                selectedText =  getOptions().getManySelectedTextMultipleMobile();
            } else {
                selectedText =  getOptions().getOneSelectedTextMultipleMobile();
            }

            selectedText = selectedText.replace("{}", "" + choices);

            getSelectedItem().find("span").text(selectedText);
        }
    }

    protected void resultDeactivate(GQuery query, boolean selected) {
        if (!selected) {
            super.resultDeactivate(query, selected);
        }
    }

    @Override
    protected void onResultSelected(OptionItem item, String newValue, String oldValue, boolean metaKeyPressed) {
        if (oldValue == null || !oldValue.equals(newValue)) {
            fireEvent(new ChosenChangeEvent(newValue, item.getArrayIndex(), this));
        }
    }
}
