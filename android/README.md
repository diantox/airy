# Airy
A free software gesture library for Android&trade;.

Copyright (C) 2015  Miras Absar

This program is free software: you can redistribute it and/or modify  
it under the terms of the GNU General Public License as published by  
the Free Software Foundation, either version 3 of the License, or  
(at your option) any later version.

This program is distributed in the hope that it will be useful,  
but WITHOUT ANY WARRANTY; without even the implied warranty of  
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  
GNU General Public License for more details.

You should have received a copy of the GNU General Public License  
along with this program.  If not, see <[http://www.gnu.org/licenses/](http://www.gnu.org/licenses/ "Licenses - GNU Project - Free Software Foundation")>.

## Implementation
```java
View mView = findViewById(R.id.view);

Airy mAiry = new Airy(this) {

    @Override
    public void onGesture(View pView, int pGestureId) {
        switch (pGestureId) {
            case Airy.UNKNOWN_GESTURE:
                break;
            case Airy.ONE_FINGER_TAP:
                break;
            case Airy.ONE_FINGER_SWIPE_UP:
                break;
            case Airy.ONE_FINGER_SWIPE_DOWN:
                break;
            case Airy.ONE_FINGER_SWIPE_LEFT:
                break;
            case Airy.ONE_FINGER_SWIPE_RIGHT:
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

};

mView.setOnTouchListener(mAiry);
```
