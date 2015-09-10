/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.smc.inputmethod.indic.personalization;

import com.android.inputmethod.keyboard.ProximityInfo;
import com.android.inputmethod.latin.PrevWordsInfo;

import org.smc.ime.InputMethod;
import org.smc.inputmethod.annotations.UsedForTesting;
import org.smc.inputmethod.indic.Dictionary;
import org.smc.inputmethod.indic.SuggestedWords;
import org.smc.inputmethod.indic.WordComposer;
import org.smc.inputmethod.indic.settings.SettingsValuesForSuggestion;

import java.util.ArrayList;
import java.util.Map;

public class TransliterationDictionary extends Dictionary {
    /* package */ static final String NAME = TransliterationDictionary.class.getSimpleName();

    private InputMethod mInputMethod;

    public TransliterationDictionary(final InputMethod inputMethod) {
        super(Dictionary.TYPE_APPLICATION_DEFINED);
        mInputMethod = inputMethod;
    }

    @UsedForTesting
    public static TransliterationDictionary getDictionary(final InputMethod inputMethod) {
        return new TransliterationDictionary(inputMethod);
    }

    @Override
    public ArrayList<SuggestedWords.SuggestedWordInfo> getSuggestions(WordComposer composer,
                                                                      PrevWordsInfo prevWordsInfo,
                                                                      ProximityInfo proximityInfo,
                                                                      SettingsValuesForSuggestion settingsValuesForSuggestion,
                                                                      int sessionId, float[] inOutLanguageWeight) {
        if (mInputMethod == null) {
            return null;
        }

        String typedWord = composer.getTypedWord();
        Map<String, Integer> suggestions = mInputMethod.getSuggestions(typedWord);
        ArrayList<SuggestedWords.SuggestedWordInfo> suggestedWordInfos = new ArrayList<>(suggestions.size());
        for (Map.Entry<String, Integer> suggestionEntry : suggestions.entrySet()) {
            SuggestedWords.SuggestedWordInfo info = new SuggestedWords.SuggestedWordInfo(suggestionEntry.getKey(),
                    - suggestionEntry.getValue(),
                    SuggestedWords.SuggestedWordInfo.KIND_OOV_CORRECTION, Dictionary.DICTIONARY_APPLICATION_DEFINED,
                    SuggestedWords.SuggestedWordInfo.NOT_AN_INDEX, SuggestedWords.SuggestedWordInfo.NOT_A_CONFIDENCE);
            suggestedWordInfos.add(info);
        }
        return suggestedWordInfos;
    }

    @Override
    public boolean isInitialized() {
        return super.isInitialized() && mInputMethod != null;
    }

    @Override
    public boolean isInDictionary(String word) {
        return false;
    }

    @Override
    public boolean isValidWord(final String word) {
        return false;
    }

    @Override
    public void close() {
        super.close();
    }
}
