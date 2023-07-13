# FileSelector

这个库是用 `Jetpack Compose` 写的，主要为了完成文件选择功能。`FileSelector` 支持选择文件、文件夹和多选文件、文件夹，并且支持返回上一层目录

## 为什么会有这个库

通常在使用到 `Jetpack Compose` 的文件选择时都会使用 `ActivityResultContracts`，例如你要选择文件时可以使用 `OpenDocument`，选择文件夹时可以使用 `OpenDocumentTree` 等等。但是作者在平时的使用中却有一些不同的需求，例如我要在某个路径(外部存储Music目录等)下去选择某些文件(apk文件等)，这些是 `ActivityResultContracts` 不能全部满足的。

## 如何使用这个库

`FileSelector` 是一个 `Compose` 函数，你可以将它放在任何 `Compose` 函数内，例如可以放到 `Dialog` 或者 `Activity` 布局下。

首先你要确保你有权限浏览你的路径。例如外部存储根目录时，Android 6.0 需要请求 `WRITE_EXTERNAL_STORAGE` 权限，Android 10 你可以设置 `requestLegacyExternalStorage` 节点，Android 11 以上你需要获取 `ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION` 权限等等。Demo 为了方便起见只是将 `targetSdk` 设置成了29，并且使用了 [accompanist](https://github.com/google/accompanist) 的 `permissions` 库请求权限。

`FileSelector` 函数需要传递 `FileSelectorData`，`onClose` 回调，`onSelectedPaths` 回调。

`FileSelectorData` 是一个数据类，`rootPath` 字段是你想选择的根目录；`isSelectFile` 是布尔值 表示选择文件还是文件夹，默认 `true` 选择文件，可以传 `false` 选择文件夹 ；`isMultiple` 是布尔值 表示是否多选，默认是 `false` 单选；`fileType` 表示你想要选择的文件后缀，例如选择 `apk` 文件就用 ".apk"，仅当 `isSelectFile= true` 才有效。 

```kotlin
data class FileSelectorData(
    val rootPath: String,
    val isSelectFile: Boolean = true,
    val isMultiple: Boolean = false,
    val fileType: String = ""
)
```

`onClose`  是一个函数类型的参数，当它点击关闭按钮是回调

`onSelectedPaths` 是一个函数类型的参数，当它点击确认按钮时回调，你可以在此拿到选择的文件或文件夹的集合。

## 截图

<img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_single_file.png" width="210px"> <img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_single_folder.png" width="210px"> <img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_multiple_file.png" width="210px"> <img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_multiple_folder.png" width="210px">