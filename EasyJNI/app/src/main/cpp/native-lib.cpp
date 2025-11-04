#include <jni.h>
#include <string>
#include <android/log.h>
#define LOG_TAG "EasyJNI"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)

//#define DEBUG


void swap_bytes(char* byte1, char* byte2){
    char tmp;
    tmp =*byte1;
    *byte1 = *byte2;
    *byte2 = tmp;
}


extern "C" JNIEXPORT jstring JNICALL
Java_cn_sjtu_oops_easyjni_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

// 标准base64码表
char base64_table[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

// 简单的shuffle函数 - 使用固定seed保证结果可重现
void shuffle_table() {
    // 设置固定seed
    srand(0);

    // 进行100次交换
    for (int i = 0; i < 100; i++) {
        int idx1 = rand() % 64;
        int idx2 = rand() % 64;
#ifdef DEBUG
        LOGD("idx1 = %2d, idx2 = %2d", idx1, idx2);
        LOGD("%2d %c %2d %c %s", idx1, base64_table[idx1], idx2, base64_table[idx2], base64_table);
#endif
//        swap_bytes(&base64_table[idx1], &base64_table[idx2]);
        // 交换字符
        base64_table[idx1] += base64_table[idx2];
        base64_table[idx2] = base64_table[idx1] - base64_table[idx2];
        base64_table[idx1] -= base64_table[idx2];
    }
}

void custom_base64_encode(const char* input, char* output, int max_size, int* output_size) {
    int input_len = strlen(input);
    int output_idx = 0;
    int val = 0, valb = -6;

    for (int i = 0; i < input_len; i++) {
        unsigned char c = input[i];
        val = (val << 8) + c;
        valb += 8;
        while (valb >= 0) {
            if (output_idx < max_size - 1) {
                output[output_idx++] = base64_table[(val >> valb) & 0x3F];
            }
            valb -= 6;
        }
    }

    if (valb > -6) {
        if (output_idx < max_size - 1) {
            output[output_idx++] = base64_table[((val << 8) >> (valb + 8)) & 0x3F];
        }
    }

    // 添加padding
    while (output_idx % 4 != 0 && output_idx < max_size - 1) {
        output[output_idx++] = '=';
    }

    output[output_idx] = '\0';
    *output_size = output_idx;
}

void custom_base64_encode_(const char* input, char* output, int max_size, int* output_size){
    int  input_len = strlen(input);
    int output_idx = 0;

    // 传统的3字节分组处理
    for (int i = 0; i < input_len; i += 3) {
    int byte1 = input[i];
    int byte2 = (i+1 < input_len) ? input[i+1] : 0;
    int byte3 = (i+2 < input_len) ? input[i+2] : 0;

    int triple = (byte1 << 16) | (byte2 << 8) | byte3;

    output[output_idx++] = base64_table[(triple >> 18) & 0x3F];
    output[output_idx++] = base64_table[(triple >> 12) & 0x3F];
    output[output_idx++] = (i+1 < input_len) ? base64_table[(triple >> 6) & 0x3F] : '=';
    output[output_idx++] = (i+2 < input_len) ? base64_table[triple & 0x3F] : '=';
    }
    *output_size = output_idx;
}

bool strings_equal(const char* str1, const char* str2) {
    return strcmp(str1, str2) == 0;
}


// 检查flag的JNI函数
extern "C" JNIEXPORT jboolean JNICALL
Java_cn_sjtu_oops_easyjni_MainActivity_checkFlag(
        JNIEnv* env,
        jobject thiz,
        jstring input) {

    // 初始化打乱码表
    shuffle_table();

    const char* user_input = (env)->GetStringUTFChars(input, JNI_FALSE);
    if (user_input == NULL) {
        return JNI_FALSE;
    }

    // 编码用户输入
    char user_encoded[0x1000];
    int encoded_size;
    custom_base64_encode(user_input, user_encoded, sizeof(user_encoded), &encoded_size);

    (env)->ReleaseStringUTFChars(input, user_input);


#ifdef DEBUG
    // 硬编码的正确flag经过打乱base64编码后的结果
    // 这里先编码正确flag
    const char* correct_flag = "flag{r4nd_and_r4nd_s4me_but_n0t_s4me?__Why!_Why,_baby!_Why!}";
    char correct_encoded[0x1000];
    int encoded_size_;

    custom_base64_encode(correct_flag, correct_encoded, sizeof(correct_encoded), &encoded_size_);

    LOGD("curflag is %s", correct_encoded);
#endif
    LOGW("NOTE: On button clicked, you need to kill the app to reset the table");
    return strings_equal(user_encoded, "qKSdqWPkohOXbw1mq1akohOXbWVl59AiCHAlbw8rf1aMohxc4xaiAwdONAabDtX7bwUdCHXdbxfLgn1a")?JNI_TRUE:JNI_FALSE;

}