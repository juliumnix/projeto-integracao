import React from 'react';
import {Alert, AppRegistry, Button, StyleSheet, Text, View} from 'react-native';

const HelloWorld = (props) => {
  return (
    <View style={styles.container}>
      <Text style={styles.hello}>Hello, ${props.message_from_native}</Text>
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

AppRegistry.registerComponent(
  'MyReactNativeApp',
  () => HelloWorld,
);