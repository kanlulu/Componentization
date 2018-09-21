/*
 * Copyright (C) 2016 venshine.cn@gmail.com
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
package com.kanlulu.common.widget.wheel;

import android.graphics.drawable.Drawable;

/**
 * @author venshine
 */
public class DrawableFactory {

    public static Drawable createDrawable(WheelView.Skin skin, int width, int height, WheelView.WheelViewStyle
            style, int wheelSize, int itemH) {
        if (skin.equals(WheelView.Skin.Common)) {
            return new CommonDrawable(width, height, style, wheelSize, itemH);
        } else if (skin.equals(WheelView.Skin.Holo)) {
            return new HoloDrawable(width, height, style, wheelSize, itemH);
        } else {
            return new WheelDrawable(width, height, style);
        }
    }

}
