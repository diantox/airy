# Airy

An open source gesture library for Android™.

## Usage

```
View mView = findViewById(R.id.view);
View mAnotherView = findViewById(R.id.anotherView);

// this has to be an instance of Activity.
Airy mAiry = new Airy(this) {
    @Override
    public void onGesture(View pView, int pGestureId) {
        if (pView == mView) {
            switch (pGestureId) {
                case Airy.INVALID_GESTURE:
                    break;
                case Airy.TAP:
                    break;
                case Airy.SWIPE_UP:
                    break;
                case Airy.SWIPE_DOWN:
                    break;
                case Airy.SWIPE_LEFT:
                    break;
                case Airy.SWIPE_RIGHT:
                    break;
                case Airy.TWO_FINGER_TAP:
                    break;
                case Airy.TWO_FINGER_SWIPE_UP:
                    break;
                case Airy.TWO_FINGER_SWIPE_DOWN:
                    break;
                case Airy.TWO_FINGER_SWIPE_LEFT:
                    break;
                case Airy.TWO_FINGER_SWIPE_RIGHT:
                    break;
                case Airy.TWO_FINGER_PINCH_IN:
                    break;
                case Airy.TWO_FINGER_PINCH_OUT:
                    break;
            }
        } else if (pView == mAnotherView) {
            switch (pGestureId) {
                case Airy.INVALID_GESTURE:
                    break;
                case Airy.TAP:
                    break;
                case Airy.SWIPE_UP:
                    break;
                case Airy.SWIPE_DOWN:
                    break;
                case Airy.SWIPE_LEFT:
                    break;
                case Airy.SWIPE_RIGHT:
                    break;
                case Airy.TWO_FINGER_TAP:
                    break;
                case Airy.TWO_FINGER_SWIPE_UP:
                    break;
                case Airy.TWO_FINGER_SWIPE_DOWN:
                    break;
                case Airy.TWO_FINGER_SWIPE_LEFT:
                    break;
                case Airy.TWO_FINGER_SWIPE_RIGHT:
                    break;
                case Airy.TWO_FINGER_PINCH_IN:
                    break;
                case Airy.TWO_FINGER_PINCH_OUT:
                    break;
            }
        }
    }
};

mView.setOnTouchListener(mAiry);
mAnotherView.setOnTouchListener(mAnotherAiry);
```

### Alternate Constructor

```
DisplayMetrics mDisplayMetrics = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
float mDisplayDensity = mDisplayMetrics.density;

Airy mAiry = new Airy(mDisplayDensity);
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