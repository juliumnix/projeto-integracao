import React from 'react';
import {AppRegistry} from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import 'react-native-gesture-handler';
import { App } from './react/Screens/App';
import { HomeScreen } from './react/Screens/HomeScreen';

const Stack = createStackNavigator();

const HelloWorld = ({ message_from_native }) => {
  
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen  name="App" component={App} initialParams={{ message_from_native }}/>
        <Stack.Screen options={{ headerShown: false }} name="HomeScreen" component={HomeScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

AppRegistry.registerComponent(
  'MyReactNativeApp',
  () => HelloWorld,
);