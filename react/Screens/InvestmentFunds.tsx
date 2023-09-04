import { View, Text } from "react-native";
import React from "react";
import { NativeModules } from "react-native";
import { Button } from "react-native";

const { NativeFunctions } = NativeModules;

export default function InvestmentFunds() {
  const navigateToFlutter = async () => {
    try {
      await NativeFunctions.navigateToFlutter();
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <View>
      <Text>InvestmentFunds</Text>
      <Button
        title="Ir para flutter"
        onPress={() => {
          navigateToFlutter();
        }}
      />
    </View>
  );
}
