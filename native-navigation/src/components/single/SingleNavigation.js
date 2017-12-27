import React from 'react';
import { NativeModules } from 'react-native';

const { ReactNativeNativeNavigation } = NativeModules;
import { Navigation } from './../Navigation';
import StackView from './../stack/StackView';
import SingleView from './../single/SingleView';
import BaseNavigation from '../BaseNavigation';

export default class SingleNavigation extends BaseNavigation{
	screenID;

	constructor(screenID) {
		super()
		this.screenID = screenID;
	}

	showModal = (showScreen) => {
		return super.registerScreens(this, Navigation, showScreen);
	}
}