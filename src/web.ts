import { Capacitor, WebPlugin } from '@capacitor/core';

import type { MyCustomPluginPlugin } from './definitions';
import { Plugins } from '@capacitor/core';

const { Camera } = Plugins;

export class MyCustomPluginWeb extends WebPlugin implements MyCustomPluginPlugin {
  callNative: any;
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async   testPluginMethod( option:{msg:string}):Promise<{ value: string }> {
    alert(option.msg);
    return{value:option.msg};
  }

 /* async takePhoto(): Promise<{ success: boolean, message: string }> {
    // Votre logique pour ouvrir la caméra sur la plate-forme web
    // Retournez un résultat ou une promesse avec le résultat ou une erreur
    return { success: true, message: 'Photo captured successfully on web' };
  }*/

  async takePhoto(): Promise<{ success: boolean, path: string }> {
    const capturedPhoto = await Camera.getPhoto({
      quality: 90,
      allowEditing: false,
      resultType: 'uri' // Utilisation du type 'uri' pour spécifier le résultat comme une URI
    });

    return { success: true, path: capturedPhoto.webPath };
  }


  async openCamera(): Promise<{ success: boolean, message: string }> {
    try {
      const stream = await navigator.mediaDevices.getUserMedia({ video: true });
      const videoElement = document.createElement('video');
      videoElement.srcObject = stream;
      document.body.appendChild(videoElement);
      videoElement.play();
      return { success: true, message: 'Camera opened successfully' };
    } catch (error) {
      console.error('Error opening camera:', error);
      return { success: false, message: 'Failed to open camera' };
    }
  }

  async startScanning(): Promise<string> {
    if (Capacitor.getPlatform()) {
      return 'Document scanning not supported on web platform. Please use a mobile device.';
    } else {
      // Call the native plugin implementation (replace with your actual call)
      const result = await this.callNative('DocumentScannerPlugin', 'startScanning', {});
      return result;
    }
  }
    
  



  
}
