declare module"@capacitor/core"{
  interface PluginRegistry {
    MyCustomPlugin:MyCustomPluginPlugin;
  }
}
export interface MyCustomPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  testPluginMethod( option:{msg:string}):Promise<{ value: string }>;
  //takePhoto( option:{msg:string}):Promise<{ value: string }>;
  takePhoto(): Promise<{ success: boolean, path: string }>;
  openCamera(): Promise<{ success: boolean, message: string }>;
  startScanning(): Promise<string>;


}
