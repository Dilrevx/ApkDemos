function hook_get_flag() {
    var MainActivity = Java.use("com.ph0en1x.android_crackme.MainActivity");
    MainActivity.getFlag.implementation = function () {
        console.log("getFlag called");
        var ret = this.getFlag();
        console.log("getFlag returned: " + ret);
        return ret;
    };
}

function main() {
    Java.perform(
        hook_get_flag
    )

}


main()