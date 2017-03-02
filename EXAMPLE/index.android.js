/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  Button,
  DeviceEventEmitter,
  NativeModules,
  StyleSheet,
  Text,
  View
} from 'react-native';
var ToastThisModule  = NativeModules.ToastThisModule;
var NfcReadModule = NativeModules.NfcReadModule;

export default class ToastExample extends Component {
  constructor(props) {
    super(props);
    this.state = {location: null};
    console.log('Constructed.')
  }

  componentDidMount(){
  //ToastThisModule.show('Awesome', ToastThisModule.LONG);
  console.log("componentDidMount.");
/*
  NfcReadModule.getCardId().then((card) => {
      console.log(card);
    }).catch((err) => {
      console.log(err);
  })*/
  DeviceEventEmitter.addListener('NFCTagDetected', (serial) => {
  console.log('DeviceEventEmitter listened.');
  this.setState({serial});
  console.log(serial);
});
}



  render() {
    const onButtonPress = () => {
NfcReadModule.show();
};

    return (
      <View>
        <Text>
          Welcome to React Native!
        </Text>
        <Button
          onPress={onButtonPress}
          title="Press Purple"
          color="#841584"
          accessibilityLabel="Learn more about purple"
        />
      </View>
    );
  }
}

AppRegistry.registerComponent('ToastExample', () => ToastExample);
