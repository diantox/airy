/**
 * Airy 2.0, a free software gesture library for the Web.
 * Copyright (C) 2015  Miras Absar
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * -------------------------------------------------------------------------------------------------
 * - Pointer ---------------------------------------------------------------------------------------
 * -------------------------------------------------------------------------------------------------
 */

function Pointer(pId,
                 pDownTime, pDownX, pDownY,
                 pMovementLimitDp) {

    this.mId = pId;

    this.mDownTime = pDownTime;
    this.mDownX = pDownX;
    this.mDownY = pDownY;

    this.mUpXUpperLimit = this.mDownX + pMovementLimitDp;
    this.mUpYUpperLimit = this.mDownY + pMovementLimitDp;
    this.mUpXLowerLimit = this.mDownX - pMovementLimitDp;
    this.mUpYLowerLimit = this.mDownY - pMovementLimitDp;
}

Pointer.prototype.setUpTime = function(pUpTime) {
    this.mUpTime = pUpTime;
};

Pointer.prototype.setUpX = function(pUpX) {
    this.mUpX = pUpX;
};

Pointer.prototype.setUpY = function(pUpY) {
    this.mUpY = pUpY;
};

Pointer.prototype.getId = function() {
    return this.mId;
};

Pointer.prototype.getDownX = function() {
    return this.mDownX;
};

Pointer.prototype.getDownY = function() {
    return this.mDownY;
};

Pointer.prototype.getUpX = function() {
    return this.mUpX;
};

Pointer.prototype.getUpY = function() {
    return this.mUpY;
};

Pointer.prototype.downInsideTimeLimit = function(pTimeLimit) {
    return (this.mUpTime - this.mDownTime) <= pTimeLimit;
};

Pointer.prototype.tapped = function() {
    return this.mUpX < this.mUpXUpperLimit &&
            this.mUpY < this.mUpYUpperLimit &&
            this.mUpX > this.mUpXLowerLimit &&
            this.mUpY > this.mUpYLowerLimit;
};

Pointer.prototype.swipedUp = function() {
    return this.mUpX < this.mUpXUpperLimit &&
            this.mUpX > this.mUpXLowerLimit &&
            this.mUpY <= this.mUpYLowerLimit;
};

Pointer.prototype.swipedDown = function() {
    return this.mUpX < this.mUpXUpperLimit &&
            this.mUpY >= this.mUpYUpperLimit &&
            this.mUpX > this.mUpXLowerLimit;
};

Pointer.prototype.swipedLeft = function() {
    return this.mUpY < this.mUpYUpperLimit &&
            this.mUpX <= this.mUpXLowerLimit &&
            this.mUpY > this.mUpYLowerLimit;
};

Pointer.prototype.swipedRight = function() {
    return this.mUpX >= this.mUpXUpperLimit &&
            this.mUpY < this.mUpYUpperLimit &&
            this.mUpY > this.mUpYLowerLimit;
};

Pointer.prototype.distanceFormula = function(pXI, pYI,
                                             pXII, pYII) {

    return Math.sqrt(Math.pow(pXI - pXII, 2) + Math.pow(pYI - pYII, 2));
};

Pointer.prototype.pinchedIn = function(pPointer, pMovementLimitDp) {
    return (this.distanceFormula(this.mDownX, this.mDownY, pPointer.getDownX(), pPointer.getDownY())
            + pMovementLimitDp) <=
            this.distanceFormula(this.mUpX, this.mUpY, pPointer.getUpX(), pPointer.getUpY());
};

Pointer.prototype.pinchedOut = function(pPointer, pMovementLimitDp) {
    return (this.distanceFormula(this.mDownX, this.mDownY, pPointer.getDownX(), pPointer.getDownY())
            - pMovementLimitDp) >=
            this.distanceFormula(this.mUpX, this.mUpY, pPointer.getUpX(), pPointer.getUpY());
};

/**
 * -------------------------------------------------------------------------------------------------
 * - Airy ------------------------------------------------------------------------------------------
 * -------------------------------------------------------------------------------------------------
 */

Airy.prototype.TIME_LIMIT = 300;
Airy.prototype.MOVEMENT_LIMIT_DP = 48;

Airy.prototype.PRIMARY_TOUCH_START = 0;
Airy.prototype.SECONDARY_TOUCH_START = 1;
Airy.prototype.SECONDARY_TOUCH_END = 2;
Airy.prototype.PRIMARY_TOUCH_END = 3;

Airy.prototype.UNKNOWN_GESTURE = 0;

Airy.prototype.ONE_FINGER_TAP = 1;
Airy.prototype.ONE_FINGER_SWIPE_UP = 2;
Airy.prototype.ONE_FINGER_SWIPE_DOWN = 3;
Airy.prototype.ONE_FINGER_SWIPE_LEFT = 4;
Airy.prototype.ONE_FINGER_SWIPE_RIGHT = 5;

Airy.prototype.TWO_FINGER_TAP = 6;
Airy.prototype.TWO_FINGER_SWIPE_UP = 7;
Airy.prototype.TWO_FINGER_SWIPE_DOWN = 8;
Airy.prototype.TWO_FINGER_SWIPE_LEFT = 9;
Airy.prototype.TWO_FINGER_SWIPE_RIGHT = 10;
Airy.prototype.TWO_FINGER_PINCH_IN = 11;
Airy.prototype.TWO_FINGER_PINCH_OUT = 12;

function Airy(pOnGesture) {
    this.mOnGesture = pOnGesture;
}

Airy.prototype.getTouchEventType = function(pTouchEvent) {
    if (pTouchEvent.type == "touchstart" && pTouchEvent.touches.length == 1) {
        return this.PRIMARY_TOUCH_START;
    } else if (pTouchEvent.type == "touchstart" && pTouchEvent.touches.length >= 2) {
        return this.SECONDARY_TOUCH_START;
    } else if (pTouchEvent.type == "touchend" && pTouchEvent.touches.length >= 1) {
        return this.SECONDARY_TOUCH_END;
    } else if (pTouchEvent.type == "touchend" && pTouchEvent.touches.length === 0) {
        return this.PRIMARY_TOUCH_END;
    }
};

Airy.prototype.getGestureId = function() {
    var mTotalPointerCount = this.mPointers.length;

    if (mTotalPointerCount == 1) {
        var mPointer = this.mPointers[0];

        if (mPointer.downInsideTimeLimit(this.TIME_LIMIT)) {
            if (mPointer.tapped()) {
                return this.ONE_FINGER_TAP;
            } else if (mPointer.swipedUp()) {
                return this.ONE_FINGER_SWIPE_UP;
            } else if (mPointer.swipedDown()) {
                return this.ONE_FINGER_SWIPE_DOWN;
            } else if (mPointer.swipedLeft()) {
                return this.ONE_FINGER_SWIPE_LEFT;
            } else if (mPointer.swipedRight()) {
                return this.ONE_FINGER_SWIPE_RIGHT;
            } else {
                return this.UNKNOWN_GESTURE;
            }
        } else {
            return this.UNKNOWN_GESTURE;
        }
    } else if (mTotalPointerCount == 2) {
        var mPointerI = this.mPointers[0];
        var mPointerII = this.mPointers[1];

        if (mPointerI.downInsideTimeLimit(this.TIME_LIMIT) &&
                mPointerII.downInsideTimeLimit(this.TIME_LIMIT)) {

            if (mPointerI.tapped() &&
                    mPointerII.tapped()) {

                return this.TWO_FINGER_TAP;

            } else if (mPointerI.swipedUp() &&
                    mPointerII.swipedUp()) {

                return this.TWO_FINGER_SWIPE_UP;

            } else if (mPointerI.swipedDown() &&
                    mPointerII.swipedDown()) {

                return this.TWO_FINGER_SWIPE_DOWN;

            } else if (mPointerI.swipedLeft() &&
                    mPointerII.swipedLeft()) {

                return this.TWO_FINGER_SWIPE_LEFT;

            } else if (mPointerI.swipedRight() &&
                    mPointerII.swipedRight()) {

                return this.TWO_FINGER_SWIPE_RIGHT;
            } else if (mPointerI.pinchedIn(mPointerII, this.MOVEMENT_LIMIT_DP)) {
                return this.TWO_FINGER_PINCH_IN;
            } else if (mPointerI.pinchedOut(mPointerII, this.MOVEMENT_LIMIT_DP)) {
                return this.TWO_FINGER_PINCH_OUT;
            } else {
                return this.UNKNOWN_GESTURE;
            }
        } else {
            return this.UNKNOWN_GESTURE;
        }
    } else {
        return this.UNKNOWN_GESTURE;
    }
};

Airy.prototype.onTouch = function(pTouchEvent) {
    var mTouch = pTouchEvent.changedTouches[0];

    var mIdentifier = mTouch.identifier;
    var mTime = new Date().getTime();
    var mScreenX = mTouch.screenX;
    var mScreenY = mTouch.screenY;

    switch (this.getTouchEventType(pTouchEvent)) {
        case this.PRIMARY_TOUCH_START:
            this.mPointers = [];

            this.mPointers.push(new Pointer(mIdentifier, mTime, mScreenX, mScreenY,
                                            this.MOVEMENT_LIMIT_DP));

            break;
        case this.SECONDARY_TOUCH_START:

            this.mPointers.push(new Pointer(mIdentifier, mTime, mScreenX, mScreenY,
                                            this.MOVEMENT_LIMIT_DP));

            break;
        case this.SECONDARY_TOUCH_END:
            for (var pIndexI = this.mPointers.length - 1; pIndexI >= 0; pIndexI--) {
                var mPointerI = this.mPointers[pIndexI];

                if (mPointerI.getId() == mIdentifier) {
                    mPointerI.setUpTime(mTime);
                    mPointerI.setUpX(mScreenX);
                    mPointerI.setUpY(mScreenY);
                }
            }
            break;
        case this.PRIMARY_TOUCH_END:
            for (var pIndexII = this.mPointers.length - 1; pIndexII >= 0; pIndexII--) {
                var mPointerII = this.mPointers[pIndexII];

                if (mPointerII.getId() == mIdentifier) {
                    mPointerII.setUpTime(mTime);
                    mPointerII.setUpX(mScreenX);
                    mPointerII.setUpY(mScreenY);
                }
            }

            this.mOnGesture(mTouch.target, this.getGestureId());
            break;
    }
};

Airy.prototype.attachTo = function(pElement) {
    var mAiry = this;

    pElement.addEventListener("touchstart", function(pTouchEvent) {mAiry.onTouch(pTouchEvent);},
                              false);

    pElement.addEventListener("touchend", function(pTouchEvent) {mAiry.onTouch(pTouchEvent);},
                              false);
};
