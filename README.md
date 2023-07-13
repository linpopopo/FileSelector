# FileSelector

[中文文档](https://github.com/linpopopo/FileSelector/blob/main/README_%E4%B8%AD%E6%96%87.md)

This library is written in `Jetpack Compose` and is mainly for file selection.`FileSelector` supports the selection of files, folders and multi-selected files, folders, and supports the return to the previous level of directories

## Why does this library exist

You usually use `ActivityResultContracts` when you use `Jetpack Compose` for file selection, for example you can use `OpenDocument` for file selection, `OpenDocumentTree` for folder selection, and so on. However, the author has some different requirements in the ordinary use of `ActivityResultContracts`. For example, I need to go to a certain path (external storage Music directory, etc.) to select certain files (apk files, etc.), which are not fully satisfied by `ActivityResultContracts`.

## How to use this library

`FileSelector` is a Compose function, and you can put it in any Compose function, such as the Dialog or Activity layout.

First you need to make sure you have permission to browse your path. Such as external storage root directory, the Android 6.0 need to request the `WRITE_EXTERNAL_STORAGE`, Android 10 you can set up the `requestLegacyExternalStorage` nodes, Android 11 more than you need to get the `ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION` and so on. Demo simply sets targetSdk to 29 for convenience and uses [accompanist's](https://github.com/google/accompanist) permissions library to request permissions.

The `FileSelector` function needs to pass `FileSelectorData`, `onClose` callback, and `onSelectedPaths` callback.

`FileSelectorData` is a data class, and the `rootPath` field is the root directory you want to select;  `isSelectFile` specifies whether to select a file or a folder. By default, true selects a file, and false selects a folder. `isMultiple` Indicates whether to select multiple. The default value is false. `fileType` indicates the fileType you want to select, such as "apk" for apk files, only if `isSelectFile = true`.

```kotlin
data class FileSelectorData(
    val rootPath: String,
    val isSelectFile: Boolean = true,
    val isMultiple: Boolean = false,
    val fileType: String = ""
)
```

`onClose` is a function type argument that is called back when it clicks the close button.

`onSelectedPaths` is a function type parameter that is called back when the confirm button is clicked, where you can get the collection of selected files or folders.

## screenshot

<img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_single_file.png" width="210px"> <img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_single_folder.png" width="210px"> <img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_multiple_file.png" width="210px"> <img src="https://github.com/linpopopo/FileSelector/blob/main/img/select_multiple_folder.png" width="210px">
