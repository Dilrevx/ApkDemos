251020 EasyJNI 出题
===

在 Java 层从文本输入框拿 string，传给 JNI 层的 check 函数检查加密后的密文和 flag 的密文是不是一样的。

加密方法为换表后的 base64，换表方法：rand() 两个 idx 然后交换位置

> 可以通过 #define DEBUG 实现出题的时候打印调试信息，打印 flag 的密文；DEBUG 目前没有集成到 CMakeLists，是直接写在 c++ 代码里的

## 出题踩坑

1. 画 UI，Android Studio 的 GUI 编辑器功能很弱，调位置还是得直接写 XML
2. 可以通过 #define DEBUG 实现出题的时候打印调试信息，打印 flag 的密文；DEBUG 目前没有集成到 CMakeLists，是直接写在 c++ 代码里的
3. rand() 两次可能是重复的，导致交换位置没有变化，减成 0x00 了
4. 由于 glic 和 bionic 的实现差异，rand() 在 JNI 层和 linux 的 libc 层表现不一样，导致加密结果不一样
5. 编译 NDK 时，strip 需要通过新建一个 `keep-symbol-file` 然后传给 CMakeLists.txt，需要手动把 `Java_` 开头的符号添加到文件中


```cmake
target_link_options(${CMAKE_PROJECT_NAME} PRIVATE
        -Wl,--strip-all
        -Wl,--retain-symbols-file=${KEEP_SYMBOLS_FILE}
)
```