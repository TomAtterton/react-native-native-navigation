import React, { Component } from 'react';
import { Navigation } from './../Navigation';

export default class DrawerView extends Component {
	static SIDE = {
		LEFT: "left",
		CENTER: "center",
		RIGHT: "right",
	}

	static mapToDictionary = (dom, path) => {
		const type = dom.type.name;
		const screenID = `${path}/${dom.props.name}`;
		const leftData = dom.props.left;
		let left;
		if (leftData) {
			left = Navigation.mapChild(leftData, `${screenID}/left`)
		}
		const centerData = dom.props.center;
		let center;
		if (centerData) {
			center = Navigation.mapChild(centerData, `${screenID}/center`);
		}
		const rightData = dom.props.right;
		let right;
		if (rightData) {
			right = Navigation.mapChild(rightData, `${screenID}/right`);
		}
		const side = dom.props.side;
		return {
			type,
			screenID,
			left,
			center,
			right,
			side
		}
	}

	static reduceScreens = (data, viewMap, pageMap) => {
		return [data.left, data.center, data.right].filter((side) => {
			return side != undefined;
		}).reduce((map, node) => {
			const viewData = viewMap[node.type];
			if (viewData) {
				const result = viewData.reduceScreens(node, viewMap, pageMap).map((view) => {
					const { screenID, screen } = view;
					const DrawerScreen = () => {
						return class extends Component {
							render() {
								const Screen = screen;
								return <Screen {...this.props} />
							}
						}
					}
					const Drawer = DrawerScreen();
					return ({
						screenID,
						screen: Drawer,
					})
				});
				return [
					...map,
					...result,
				]
			} else {
				return null;
			}
		}, []).filter((screen) => screen != null);
	}
}