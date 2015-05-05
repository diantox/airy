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

/**
 * A Pointer represents a point of contact between a user and a touch screen.
 */
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

    private boolean mTapped;
    private boolean mSwipedUp;
    private boolean mSwipedDown;
    private boolean mSwipedLeft;
    private boolean mSwipedRight;

    /**
     * Creates a new Pointer.
     *
     * @param pId The Pointer's ID.
     * @param pDownTime The time (in milliseconds) when the Pointer went down.
     * @param pDownX The X coordinate (in pixels) where the Pointer went down.
     * @param pDownY The Y coordinate (in pixels) where the Pointer went down.
     * @param pMovementLimitPx The distance (in pixels) the Pointer has to move to
     *                         trigger a gesture.
     */
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

    /**
     * Sets the time (in milliseconds) and coordinates (in pixels) when and where
     * the Pointer went up.
     *
     * @param pUpTime The time (in milliseconds) when the Pointer went up.
     * @param pUpX The X coordinate (in pixels) where the Pointer went up.
     * @param pUpY The Y coordinate (in pixels) where the Pointer went up.
     */
    public void setUpInfo(long pUpTime, float pUpX, float pUpY) {
        mUpTime = pUpTime;
        mUpX = pUpX;
        mUpY = pUpY;

        mTapped = mUpX < mUpXUpperLimit &&
                mUpY < mUpYUpperLimit &&
                mUpX > mUpXLowerLimit &&
                mUpY > mUpYLowerLimit;

        mSwipedUp = mUpX < mUpXUpperLimit &&
                mUpX > mUpXLowerLimit &&
                mUpY <= mUpYLowerLimit;

        mSwipedDown = mUpX < mUpXUpperLimit &&
                mUpY >= mUpYUpperLimit &&
                mUpX > mUpXLowerLimit;

        mSwipedLeft = mUpY < mUpYUpperLimit &&
                mUpX <= mUpXLowerLimit &&
                mUpY > mUpYLowerLimit;

        mSwipedRight = mUpX >= mUpXUpperLimit &&
                mUpY < mUpYUpperLimit &&
                mUpY > mUpYLowerLimit;
    }

    /**
     * Returns the Pointer's ID.
     *
     * @return The Pointer's ID.
     */
    public int getId() {
        return mId;
    }

    /**
     * Returns the X coordinate (in pixels) where the Pointer went down.
     *
     * @return The X coordinate (in pixels) where the Pointer went down.
     */
    public float getDownX() {
        return mDownX;
    }

    /**
     * Returns the Y coordinate (in pixels) where the Pointer went down.
     *
     * @return The Y coordinate (in pixels) where the Pointer went down.
     */
    public float getDownY() {
        return mDownY;
    }

    /**
     * Returns the X coordinate (in pixels) where the Pointer went up.
     *
     * @return The X coordinate (in pixels) where the Pointer went up.
     */
    public float getUpX() {
        return mUpX;
    }

    /**
     * Returns the Y coordinate (in pixels) where the Pointer went up.
     *
     * @return The Y coordinate (in pixels) where the Pointer went up.
     */
    public float getUpY() {
        return mUpY;
    }

    /**
     * Returns whether the Pointer was down inside a given time limit.
     *
     * @param pTimeLimit The time (in milliseconds) the Pointer has to perform a
     *                   gesture.
     * @return Whether the Pointer was down inside a given time limit.
     */
    public boolean downInsideTimeLimit(int pTimeLimit) {
        return (mUpTime - mDownTime) <= pTimeLimit;
    }

    /**
     * Returns whether the Pointer tapped.
     *
     * @return Whether the Pointer tapped.
     */
    public boolean getTapped() {
        return mTapped;
    }

    /**
     * Returns whether the Pointer swiped up.
     *
     * @return Whether the Pointer swiped up.
     */
    public boolean getSwipedUp() {
        return mSwipedUp;
    }

    /**
     * Returns whether the Pointer swiped down.
     *
     * @return Whether the Pointer swiped down.
     */
    public boolean getSwipedDown() {
        return mSwipedDown;
    }

    /**
     * Returns whether the Pointer swiped left.
     *
     * @return Whether the Pointer swiped left.
     */
    public boolean getSwipedLeft() {
        return mSwipedLeft;
    }

    /**
     * Returns whether the Pointer swiped right.
     *
     * @return Whether the Pointer swiped right.
     */
    public boolean getSwipedRight() {
        return mSwipedRight;
    }

    private double distanceFormula(float pXI, float pYI,
                                   float pXII, float pYII) {

        return Math.sqrt(Math.pow(pXI - pXII, 2) + Math.pow(pYI - pYII, 2));
    }

    /**
     * Returns whether the Pointer pinched in with another Pointer.
     *
     * @param pPointer Another Pointer.
     * @param pMovementLimitPx The distance (in pixels) both Pointers have to move to
     *                         trigger a gesture.
     * @return Whether the Pointer pinched in with another Pointer.
     */
    public boolean pinchedIn(Pointer pPointer, float pMovementLimitPx) {
        return (distanceFormula(mDownX, mDownY, pPointer.getDownX(), pPointer.getDownY()) +
                pMovementLimitPx) <=
                distanceFormula(mUpX, mUpY, pPointer.getUpX(), pPointer.getUpY());
    }

    /**
     * Returns whether the Pointer pinched out with another Pointer.
     *
     * @param pPointer Another Pointer.
     * @param pMovementLimitPx The distance (in pixels) both Pointers have to move to
     *                         trigger a gesture.
     * @return Whether the Pointer pinched out with another Pointer.
     */
    public boolean pinchedOut(Pointer pPointer, float pMovementLimitPx) {
        return (distanceFormula(mDownX, mDownY, pPointer.getDownX(), pPointer.getDownY()) -
                pMovementLimitPx) >=
                distanceFormula(mUpX, mUpY, pPointer.getUpX(), pPointer.getUpY());
    }

}
