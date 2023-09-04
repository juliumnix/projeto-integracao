import { View, Text, Button } from "react-native";
import React from "react";
import { useNavigation, RouteProp } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { RootStackParamList } from "./App";

type ConfirmationScreenRouteProp = RouteProp<
  RootStackParamList,
  "ConfirmationScreen"
>;

type ConfirmationScreenNavigationProp = StackNavigationProp<
  RootStackParamList,
  "ConfirmationScreen"
>;

type ConfirmationScreenProps = {
  route: ConfirmationScreenRouteProp;
  navigation: ConfirmationScreenNavigationProp;
};

export default function ConfirmationScreen({ route }: ConfirmationScreenProps) {
  const { pixCode } = route.params;
  const navigation = useNavigation<ConfirmationScreenNavigationProp>();
  return (
    <View>
      <Text>{pixCode}</Text>
      <Button
        title="ir para outrea tela"
        onPress={() => {
          navigation.navigate("InvestmentFunds");
        }}
      />
    </View>
  );
}
