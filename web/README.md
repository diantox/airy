# Airy

An open source gesture library for the web.

## Usage

```
var mElement = document.getElementById("element");
var mAnotherElement = document.getElementById("anotherElement");

var mOnGesture = function(pElement, pGestureId) {
    if (pElement == mElement) {
        switch (pGestureId) {
            case Airy.prototype.INVALID_GESTURE:
                break;
            case Airy.prototype.TAP:
                break;
            case Airy.prototype.SWIPE_UP:
                break;
            case Airy.prototype.SWIPE_DOWN:
                break;
            case Airy.prototype.SWIPE_LEFT:
                break;
            case Airy.prototype.SWIPE_RIGHT:
                break;
            case Airy.prototype.TWO_FINGER_TAP:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_UP:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_DOWN:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_LEFT:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_RIGHT:
                break;
            case Airy.prototype.TWO_FINGER_PINCH_IN:
                break;
            case Airy.prototype.TWO_FINGER_PINCH_OUT:
                break;
        }
    } else if (pElement == mAnotherElement) {
        switch (pGestureId) {
            case Airy.prototype.INVALID_GESTURE:
                break;
            case Airy.prototype.TAP:
                break;
            case Airy.prototype.SWIPE_UP:
                break;
            case Airy.prototype.SWIPE_DOWN:
                break;
            case Airy.prototype.SWIPE_LEFT:
                break;
            case Airy.prototype.SWIPE_RIGHT:
                break;
            case Airy.prototype.TWO_FINGER_TAP:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_UP:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_DOWN:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_LEFT:
                break;
            case Airy.prototype.TWO_FINGER_SWIPE_RIGHT:
                break;
            case Airy.prototype.TWO_FINGER_PINCH_IN:
                break;
            case Airy.prototype.TWO_FINGER_PINCH_OUT:
                break;
        }
    }
};

var mAiry = new Airy(mOnGesture);
mAiry.attachTo(mElement);
mAiry.attachTo(mAnotherElement);
```

## Copyright Information

```
Copyright 2014 Miras Absar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```