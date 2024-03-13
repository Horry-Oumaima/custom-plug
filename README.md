# my-custom-plugin

plugin for scan documents

## Install

```bash
npm install my-custom-plugin
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`testPluginMethod(...)`](#testpluginmethod)
* [`takePhoto()`](#takephoto)
* [`openCamera()`](#opencamera)
* [`startScanning()`](#startscanning)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### testPluginMethod(...)

```typescript
testPluginMethod(option: { msg: string; }) => Promise<{ value: string; }>
```

| Param        | Type                          |
| ------------ | ----------------------------- |
| **`option`** | <code>{ msg: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### takePhoto()

```typescript
takePhoto() => Promise<{ success: boolean; path: string; }>
```

**Returns:** <code>Promise&lt;{ success: boolean; path: string; }&gt;</code>

--------------------


### openCamera()

```typescript
openCamera() => Promise<{ success: boolean; message: string; }>
```

**Returns:** <code>Promise&lt;{ success: boolean; message: string; }&gt;</code>

--------------------


### startScanning()

```typescript
startScanning() => Promise<string>
```

**Returns:** <code>Promise&lt;string&gt;</code>

--------------------

</docgen-api>
