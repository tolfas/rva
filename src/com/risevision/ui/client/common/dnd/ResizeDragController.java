package com.risevision.ui.client.common.dnd;

/*
 * Copyright 2009 Fred Sauer
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.risevision.ui.client.common.dnd.WindowPanel.DirectionConstant;

import com.allen_sauer.gwt.dnd.client.AbstractDragController;
import com.allen_sauer.gwt.dnd.client.drop.BoundaryDropController;

import java.util.HashMap;

public final class ResizeDragController extends AbstractDragController {

	private static final int MIN_WIDGET_SIZE = 8;

	private HashMap<Widget, DirectionConstant> directionMap = new HashMap<Widget, DirectionConstant>();

	private WindowPanel windowPanel = null;

	public ResizeDragController(AbsolutePanel boundaryPanel) {
		super(boundaryPanel);
	}

	public void dragMove() {
		boolean shiftKeyPressed = windowPanel.isShiftKeyPressed();
		int direction = ((ResizeDragController) context.dragController).getDirection(context.draggable).directionBits;
		
		if ((direction & WindowPanel.DIRECTION_NORTH) != 0) {
			int delta = context.draggable.getAbsoluteTop() - context.desiredDraggableY;
			if (delta != 0) {
				int contentHeight = windowPanel.getContentHeight();
				int newHeight = Math.max(contentHeight + delta, MIN_WIDGET_SIZE);
				if (newHeight != contentHeight) {
					windowPanel.moveBy(0, contentHeight - newHeight);
				}
				windowPanel.setPixelSize(windowPanel.getContentWidth(), newHeight);

				if (shiftKeyPressed) {
					int contentWidth = windowPanel.getContentWidth();
				
					int newWidth = Math.max((int) Math.round(contentWidth * (newHeight * 1.0 / contentHeight)), MIN_WIDGET_SIZE);
					windowPanel.setPixelSize(newWidth, newHeight);
				}
			}
		} else if ((direction & WindowPanel.DIRECTION_SOUTH) != 0) {
			int delta = context.desiredDraggableY - context.draggable.getAbsoluteTop();
			if (delta != 0) {
				int contentHeight = windowPanel.getContentHeight();
				int newHeight = Math.max(contentHeight + delta, MIN_WIDGET_SIZE);

				windowPanel.setPixelSize(windowPanel.getContentWidth(), newHeight);
				
				if (shiftKeyPressed) {
					int contentWidth = windowPanel.getContentWidth();
				
					int newWidth = Math.max((int) Math.round(contentWidth * (newHeight * 1.0 / contentHeight)), MIN_WIDGET_SIZE);
					windowPanel.setPixelSize(newWidth, newHeight);
				}
			}
		}

		if ((direction & WindowPanel.DIRECTION_WEST) != 0) {
			int delta = context.draggable.getAbsoluteLeft()	- context.desiredDraggableX;
			if (delta != 0) {
				int contentWidth = windowPanel.getContentWidth();
				int newWidth = Math.max(contentWidth + delta, MIN_WIDGET_SIZE);
				if (newWidth != contentWidth) {
					windowPanel.moveBy(contentWidth - newWidth, 0);
				}
				windowPanel.setPixelSize(newWidth, windowPanel.getContentHeight());
				
				if (shiftKeyPressed) {					
					int contentHeight = windowPanel.getContentHeight();
				
					int newHeight = Math.max((int) Math.round(contentHeight * (newWidth * 1.0 / contentWidth)), MIN_WIDGET_SIZE);
					windowPanel.setPixelSize(newWidth, newHeight);
				}
			}
		} else if ((direction & WindowPanel.DIRECTION_EAST) != 0) {
			int delta = context.desiredDraggableX - context.draggable.getAbsoluteLeft();
			if (delta != 0) {
				int contentWidth = windowPanel.getContentWidth();
				int newWidth = Math.max(contentWidth + delta, MIN_WIDGET_SIZE);

				windowPanel.setPixelSize(newWidth, windowPanel.getContentHeight());
				
				if (shiftKeyPressed) {
					int contentHeight = windowPanel.getContentHeight();
				
					int newHeight = Math.max((int) Math.round(contentHeight * (newWidth * 1.0 / contentWidth)), MIN_WIDGET_SIZE);
					
					windowPanel.setPixelSize(newWidth, newHeight);
				}
			}
		}
	}

	@Override
	public void dragStart() {
		super.dragStart();
		windowPanel = (WindowPanel) context.draggable.getParent().getParent();
	}

	public void makeDraggable(Widget widget,
			WindowPanel.DirectionConstant direction) {
		super.makeDraggable(widget);
		directionMap.put(widget, direction);
	}

	protected BoundaryDropController newBoundaryDropController(
			AbsolutePanel boundaryPanel, boolean allowDroppingOnBoundaryPanel) {
		if (allowDroppingOnBoundaryPanel) {
			throw new IllegalArgumentException();
		}
		return new BoundaryDropController(boundaryPanel, false);
	}

	private DirectionConstant getDirection(Widget draggable) {
		return directionMap.get(draggable);
	}
}
