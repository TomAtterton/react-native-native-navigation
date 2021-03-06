import React, { Component } from 'react';
import { DrawerView, Navigation, SingleView } from '../native-navigation';
import Home from './pages/Home';
import Menu from './pages/Menu';
import Loading from './pages/Loading';

import { Provider } from 'react-redux';
import store from './redux/store';
import ExampleView from './custom_nodes/ExampleView';

export default class example extends Component {
	render() {
		return (
			<Navigation
				pages={[Loading, Home, Menu]}
				customViews={[ExampleView]}
				provider={Provider}
				store={store}>
				<Loading/>
				<DrawerView
					id='drawer_view'
					left={<SingleView id='menu' screen={Menu}/>}
					center={<Home/>}
					right={<ExampleView id='menu' screen={Menu}/>}
				/>
			</Navigation>
		);
	}
}
