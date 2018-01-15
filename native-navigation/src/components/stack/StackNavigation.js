import React from 'react';
import { NativeModules } from 'react-native';

const { ReactNativeNativeNavigation } = NativeModules;

import BaseNavigation from '../BaseNavigation';

export default class StackNavigation extends BaseNavigation{
	handleBackButton = (callback) => {
		ReactNativeNativeNavigation.handleBackButton(callback);
	};

	push = (showScreen) => {
		// ReactNativeNativeNavigation.callMethodOnNode(this.screenID, "push", {"screen" : this.navigation.mapChild(showScreen, this.screenID)}, (response) => {
		// 	console.log('done')
		// });
		// return super.registerScreens(this.screenID, ReactNativeNativeNavigation.push, showScreen);
        const screenData = this.navigation.mapChild(showScreen, this.screenID);

        return ReactNativeNativeNavigation.callMethodOnNode(this.screenID, "push", {"screen": this.navigation.mapChild(showScreen, this.screenID)}, (register) => {
            const viewMap = this.navigation.viewMap;
            const view = viewMap[register.type];
            const registerScreens = view.reduceScreens(register, viewMap, this.navigation.pageMap).filter((screen) => {
                return screen.screenID.includes(this.screenID) && screen.screenID !== this.screenID;
            });
            this.navigation.registerScreens(registerScreens);
        })
	}
}
