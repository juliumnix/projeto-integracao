import React from "react";
import { NativeProps } from "../..";
import { NativeModules } from "react-native";
import { NavigationContainer, RouteProp } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import ConfirmationScreen from "./ConfirmationScreen";
import InvestmentFunds from "./InvestmentFunds";
import SplashScreen from "./SplashScreen";

export type RootStackParamList = {
  SplashScreen: { infoFromNative: string };
  ConfirmationScreen: { pixCode: string };
  InvestmentFunds: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();
export const App = (props: NativeProps) => {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="SplashScreen"
          component={SplashScreen}
          initialParams={{ infoFromNative: props.message_from_native }}
        />
        <Stack.Screen
          name="ConfirmationScreen"
          component={ConfirmationScreen}
        />
        <Stack.Screen name="InvestmentFunds" component={InvestmentFunds} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};
