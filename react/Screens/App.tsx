import React from 'react';
import { Button, StyleSheet, Text, View} from 'react-native';
import { HomeText } from './styles';
import { useNavigation } from '@react-navigation/native';
import 'react-native-gesture-handler';

export const App = ({ route }: any)  => {
    const { message_from_native } = route.params;
    const navigation = useNavigation();
    return (
      <View style={styles.container}>
        <Text style={styles.hello}>BOM DIA, {message_from_native}</Text>
        <HomeText>Hello, {message_from_native}</HomeText>
        <Button title='SAFADO' onPress={() => {navigation.navigate("HomeScreen")}}/>
      </View>
    );
  };
  
  const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: 'center',
    },
    hello: {
      fontSize: 20,
      textAlign: 'center',
      margin: 10,
    },
  });