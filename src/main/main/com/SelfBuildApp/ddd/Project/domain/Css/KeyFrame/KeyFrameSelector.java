package com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame;

import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.selector.FromSelector;
import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.selector.PercentSelector;
import com.SelfBuildApp.ddd.Project.domain.Css.KeyFrame.selector.ToSelector;
import com.SelfBuildApp.ddd.Project.domain.KeyFrame;
import com.SelfBuildApp.ddd.Project.domain.PseudoSelector;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public abstract class KeyFrameSelector {

    public abstract String getValue();

    public static class Factory {

        public static KeyFrameSelector create(PseudoSelector pseudoSelector) {

            Map<String, Class> selectorNames = new HashMap<>(
                    Map.of(
                            FromSelector.NAME, FromSelector.class,
                            PercentSelector.NAME, PercentSelector.class,
                            ToSelector.NAME, ToSelector.class
                            )
            );
            Class selectorClass = null;
            for (Map.Entry<String, Class> el : selectorNames.entrySet()) {
                if (pseudoSelector.getName().equals(el.getKey())) {
                    selectorClass =  el.getValue();
                }
            }

            if (selectorClass == null) {
                return null;
            }

            if (selectorClass == PercentSelector.class) {
                return new PercentSelector(Integer.valueOf(pseudoSelector.getValue()));
            }

            Constructor<?> ctor = null;
            try {
                ctor = selectorClass.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            Object object = null;
            try {
                object = ctor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return (KeyFrameSelector) object;

        }
    }
}
