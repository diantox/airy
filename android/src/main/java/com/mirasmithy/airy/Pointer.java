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

public class Pointer {

    private int mId;

    private long mDownTime;
    private float mDownX;
    private float mDownY;

    private long mUpTime;
    private float mUpX;
    private float mUpY;

    private float mUpXUpperLimit;
    private float mUpYUpperLimit;
    private float mUpXLowerLimit;
    private float mUpYLowerLimit;

    public Pointer(int pId,
                   long pDownTime, float pDownX, float pDownY,
                   float pMovementLimitPx) {

        mId = pId;

        mDownTime = pDownTime;
        mDownX = pDownX;
        mDownY = pDownY;

        mUpXUpperLimit = mDownX + pMovementLimitPx;
        mUpYUpperLimit = mDownY + pMovementLimitPx;
        mUpXLowerLimit = mDownX - pMovementLimitPx;
        mUpYLowerLimit = mDownY - pMovementLimitPx;
    }

    public void setUpTime(long pUpTime) {
        mUpTime = pUpTime;
    }

    public void setUpX(float pUpX) {
        mUpX = pUpX;
    }

    public void setUpY(float pUpY) {
        mUpY = pUpY;
    }

    public int getId() {
        return mId;
    }

    public float getDownX() {
        return mDownX;
    }

    public float getDownY() {
        return mDownY;
    }

    public float getUpX() {
        return mUpX;
    }

    public float getUpY() {
        return mUpY;
    }

    public boolean downInsideTimeLimit(int pTimeLimit) {
        return (mUpTime - mDownTime) <= pTimeLimit;
    }

    public boolean tapped() {
        return mUpX < mUpXUpperLimit &&
                mUpY < mUpYUpperLimit &&
                mUpX > mUpXLowerLimit &&
                mUpY > mUpYLowerLimit;
    }

    public boolean swipedUp() {
        return mUpX < mUpXUpperLimit &&
                mUpX > mUpXLowerLimit &&
                mUpY <= mUpYLowerLimit;
    }

    public boolean swipedDown() {
        return mUpX < mUpXUpperLimit &&
                mUpY >= mUpYUpperLimit &&
                mUpX > mUpXLowerLimit;
    }

    public boolean swipedLeft() {
        return mUpY < mUpYUpperLimit &&
                mUpX <= mUpXLowerLimit &&
                mUpY > mUpYLowerLimit;
    }

    public boolean swipedRight() {
        return mUpX >= mUpXUpperLimit &&
                mUpY < mUpYUpperLimit &&
                mUpY > mUpYLowerLimit;
    }

    private double distanceFormula(float pXI, float pYI,
                                  float pXII, float pYII) {

        return Math.sqrt(Math.pow(pXI - pXII, 2) + Math.pow(pYI - pYII, 2));
    }

    public boolean pinchedIn(Pointer pPointer, float pMovementLimitPx) {
        return (distanceFormula(mDownX, mDownY, pPointer.getDownX(), pPointer.getDownY()) +
                pMovementLimitPx) <=
                distanceFormula(mUpX, mUpY, pPointer.getUpX(), pPointer.getUpY());
    }

    public boolean pinchedOut(Pointer pPointer, float pMovementLimitPx) {
        return (distanceFormula(mDownX, mDownY, pPointer.getDownX(), pPointer.getDownY()) -
                pMovementLimitPx) >=
                distanceFormula(mUpX, mUpY, pPointer.getUpX(), pPointer.getUpY());
    }

}
