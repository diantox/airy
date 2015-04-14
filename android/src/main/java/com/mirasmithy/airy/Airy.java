/**
 * Airy, a free software gesture library for Android™.
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

package com.mirasmithy.airy;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * An Airy is a gesture listener.
 */
public class Airy implements View.OnTouchListener {

    private static final int TIME_LIMIT = 300;
    private static final int MOVEMENT_LIMIT_DP = 48;

    /**
     * The gesture ID for an unknown gesture.
     */
    public static final int UNKNOWN_GESTURE = 0;

    /**
     * The gesture ID for a one finger tap.
     */
    public static final int ONE_FINGER_TAP = 1;

    /**
     * The gesture ID for a one finger swipe up.
     */
    public static final int ONE_FINGER_SWIPE_UP = 2;

    /**
     * The gesture ID for a one finger swipe down.
     */
    public static final int ONE_FINGER_SWIPE_DOWN = 3;

    /**
     * The gesture ID for a one finger swipe left.
     */
    public static final int ONE_FINGER_SWIPE_LEFT = 4;

    /**
     * The gesture ID for a one finger swipe right.
     */
    public static final int ONE_FINGER_SWIPE_RIGHT = 5;

    /**
     * The gesture ID for a two finger tap.
     */
    public static final int TWO_FINGER_TAP = 6;

    /**
     * The gesture ID for a two finger swipe up.
     */
    public static final int TWO_FINGER_SWIPE_UP = 7;

    /**
     * The gesture ID for a two finger swipe down.
     */
    public static final int TWO_FINGER_SWIPE_DOWN = 8;

    /**
     * The gesture ID for a two finger swipe left.
     */
    public static final int TWO_FINGER_SWIPE_LEFT = 9;

    /**
     * The gesture ID for a two finger swipe right.
     */
    public static final int TWO_FINGER_SWIPE_RIGHT = 10;

    /**
     * The gesture ID for a two finger pinch in.
     */
    public static final int TWO_FINGER_PINCH_IN = 11;

    /**
     * The gesture ID for a two finger pinch out.
     */
    public static final int TWO_FINGER_PINCH_OUT = 12;

    private float mMovementLimitPx;

    private ArrayList<Pointer> mPointers;

    /**
     * Creates a new Airy.
     *
     * @param pActivity An Activity.
     */
    public Airy(Activity pActivity) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        pActivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);

        mMovementLimitPx = MOVEMENT_LIMIT_DP * mDisplayMetrics.density;
    }

    private int getGestureId() {
        int mTotalPointerCount = mPointers.size();

        if (mTotalPointerCount == 1) {
            Pointer mPointer = mPointers.get(0);

            if (mPointer.downInsideTimeLimit(TIME_LIMIT)) {
                if (mPointer.tapped()) {
                    return ONE_FINGER_TAP;
                } else if (mPointer.swipedUp()) {
                    return ONE_FINGER_SWIPE_UP;
                } else if (mPointer.swipedDown()) {
                    return ONE_FINGER_SWIPE_DOWN;
                } else if (mPointer.swipedLeft()) {
                    return ONE_FINGER_SWIPE_LEFT;
                } else if (mPointer.swipedRight()) {
                    return ONE_FINGER_SWIPE_RIGHT;
                } else {
                    return UNKNOWN_GESTURE;
                }
            } else {
                return UNKNOWN_GESTURE;
            }
        } else if (mTotalPointerCount == 2) {
            Pointer mPointerI = mPointers.get(0);
            Pointer mPointerII = mPointers.get(1);

            if (mPointerI.downInsideTimeLimit(TIME_LIMIT) &&
                    mPointerII.downInsideTimeLimit(TIME_LIMIT)) {

                if (mPointerI.tapped() &&
                        mPointerII.tapped()) {

                    return TWO_FINGER_TAP;

                } else if (mPointerI.swipedUp() &&
                        mPointerII.swipedUp()) {

                    return TWO_FINGER_SWIPE_UP;

                } else if (mPointerI.swipedDown() &&
                        mPointerII.swipedDown()) {

                    return TWO_FINGER_SWIPE_DOWN;

                } else if (mPointerI.swipedLeft() &&
                        mPointerII.swipedLeft()) {

                    return TWO_FINGER_SWIPE_LEFT;

                } else if (mPointerI.swipedRight() &&
                        mPointerII.swipedRight()) {

                    return TWO_FINGER_SWIPE_RIGHT;
                } else if (mPointerI.pinchedIn(mPointerII, mMovementLimitPx)) {
                    return TWO_FINGER_PINCH_IN;
                } else if (mPointerI.pinchedOut(mPointerII, mMovementLimitPx)) {
                    return TWO_FINGER_PINCH_OUT;
                } else {
                    return UNKNOWN_GESTURE;
                }
            } else {
                return UNKNOWN_GESTURE;
            }
        } else {
            return UNKNOWN_GESTURE;
        }
    }

    /**
     * The function called on a gesture.
     *
     * @param pView The View a gesture was performed on.
     * @param pGestureId The ID of a performed gesture.
     */
    public void onGesture(View pView, int pGestureId) {
    }

    /**
     * The function called on a touch.
     *
     * @param pView The View a touch was performed on.
     * @param pMotionEvent The MotionEvent of a performed touch.
     * @return true
     */
    @Override
    public boolean onTouch(View pView, MotionEvent pMotionEvent) {
        int mActionIndex = pMotionEvent.getActionIndex();

        int mPointerId = pMotionEvent.getPointerId(mActionIndex);
        long mEventTime = pMotionEvent.getEventTime();
        float mX = pMotionEvent.getX(mActionIndex);
        float mY = pMotionEvent.getY(mActionIndex);

        switch (pMotionEvent.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mPointers = new ArrayList<Pointer>();

                mPointers.add(new Pointer(mPointerId, mEventTime, mX, mY, mMovementLimitPx));
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mPointers.add(new Pointer(mPointerId, mEventTime, mX, mY, mMovementLimitPx));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                for (int pIndex = mPointers.size() - 1; pIndex >= 0; pIndex--) {
                    Pointer mPointer = mPointers.get(pIndex);

                    if (mPointer.getId() == mPointerId) {
                        mPointer.setUpTime(mEventTime);
                        mPointer.setUpX(mX);
                        mPointer.setUpY(mY);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                for (int pIndex = mPointers.size() - 1; pIndex >= 0; pIndex--) {
                    Pointer mPointer = mPointers.get(pIndex);

                    if (mPointer.getId() == mPointerId) {
                        mPointer.setUpTime(mEventTime);
                        mPointer.setUpX(mX);
                        mPointer.setUpY(mY);
                    }
                }

                onGesture(pView, getGestureId());
                break;
        }

        return true;
    }

}
