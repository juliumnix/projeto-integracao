import React from 'react';
import { Button, StyleSheet, Text, View} from 'react-native';
import { NativeProps } from '../..';

import { NativeModules } from 'react-native';
const {CalendarModule} = NativeModules;


export const App = (props : NativeProps)  => {


    const createCalendarEventPromise = async () => {
      try {
        const result = await CalendarModule.createCalendarPromise();
        console.log(result)
      } catch (error) {
        console.log(error)
      }
    }  
    return (
      <View style={styles.container}>
        <Text style={styles.hello}>Welcome to React Native</Text>
        <Text style={styles.hello}>
          This is the message coming from the native
        </Text>
        <Text style={styles.nativeText}>{props.message_from_native}</Text>
        <Button title='teste' onPress={() => {createCalendarEventPromise()}}/>
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
    nativeText: {
      fontSize: 20,
      textAlign: 'center',
      fontWeight: 'bold'
    }
  });