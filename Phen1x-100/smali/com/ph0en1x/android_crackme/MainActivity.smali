.class public Lcom/ph0en1x/android_crackme/MainActivity;
.super Landroid/support/v7/app/AppCompatActivity;
.source "MainActivity.java"


# instance fields
.field etFlag:Landroid/widget/EditText;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 18
    const-string v0, "phcm"

    invoke-static {v0}, Ljava/lang/System;->loadLibrary(Ljava/lang/String;)V

    .line 19
    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 13
    invoke-direct {p0}, Landroid/support/v7/app/AppCompatActivity;-><init>()V

    return-void
.end method


# virtual methods
.method public native encrypt(Ljava/lang/String;)Ljava/lang/String;
.end method

.method public native getFlag()Ljava/lang/String;
.end method

.method public getSecret(Ljava/lang/String;)Ljava/lang/String;
    .locals 11
    .param p1, "string"    # Ljava/lang/String;

    .prologue
    .line 44
    const-string v7, "KE3TLNE6M43EK4GM34LKMLETG"

    .line 46
    .local v7, "met":Ljava/lang/String;
    :try_start_0
    invoke-virtual {p0, v7}, Lcom/ph0en1x/android_crackme/MainActivity;->encrypt(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v8

    const/4 v9, 0x5

    const/16 v10, 0x8

    invoke-virtual {v8, v9, v10}, Ljava/lang/String;->substring(II)Ljava/lang/String;

    move-result-object v8

    invoke-static {v8}, Ljava/security/MessageDigest;->getInstance(Ljava/lang/String;)Ljava/security/MessageDigest;

    move-result-object v8

    const-string v9, "UTF-8"

    invoke-virtual {p1, v9}, Ljava/lang/String;->getBytes(Ljava/lang/String;)[B

    move-result-object v9

    invoke-virtual {v8, v9}, Ljava/security/MessageDigest;->digest([B)[B

    move-result-object v3

    .line 47
    .local v3, "hash":[B
    if-eqz v3, :cond_2

    .line 48
    new-instance v4, Ljava/lang/StringBuilder;

    array-length v8, v3

    mul-int/lit8 v8, v8, 0x2

    invoke-direct {v4, v8}, Ljava/lang/StringBuilder;-><init>(I)V

    .line 49
    .local v4, "hex":Ljava/lang/StringBuilder;
    move-object v0, v3

    .local v0, "arr$":[B
    array-length v6, v0

    .local v6, "len$":I
    const/4 v5, 0x0

    .local v5, "i$":I
    :goto_0
    if-ge v5, v6, :cond_1

    aget-byte v1, v0, v5

    .line 50
    .local v1, "b":B
    and-int/lit16 v8, v1, 0xff

    const/16 v9, 0x10

    if-ge v8, v9, :cond_0

    const-string v8, "0"

    invoke-virtual {v4, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 51
    :cond_0
    and-int/lit16 v8, v1, 0xff

    invoke-static {v8}, Ljava/lang/Integer;->toHexString(I)Ljava/lang/String;

    move-result-object v8

    invoke-virtual {v4, v8}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 49
    add-int/lit8 v5, v5, 0x1

    goto :goto_0

    .line 53
    .end local v1    # "b":B
    :cond_1
    invoke-virtual {v4}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;
    :try_end_0
    .catch Ljava/security/NoSuchAlgorithmException; {:try_start_0 .. :try_end_0} :catch_0
    .catch Ljava/io/UnsupportedEncodingException; {:try_start_0 .. :try_end_0} :catch_1

    move-result-object v8

    .line 60
    .end local v0    # "arr$":[B
    .end local v3    # "hash":[B
    .end local v4    # "hex":Ljava/lang/StringBuilder;
    .end local v5    # "i$":I
    .end local v6    # "len$":I
    :goto_1
    return-object v8

    .line 55
    :catch_0
    move-exception v2

    .line 56
    .local v2, "e":Ljava/security/NoSuchAlgorithmException;
    invoke-virtual {v2}, Ljava/security/NoSuchAlgorithmException;->printStackTrace()V

    .line 60
    .end local v2    # "e":Ljava/security/NoSuchAlgorithmException;
    :cond_2
    :goto_2
    const/4 v8, 0x0

    goto :goto_1

    .line 57
    :catch_1
    move-exception v2

    .line 58
    .local v2, "e":Ljava/io/UnsupportedEncodingException;
    invoke-virtual {v2}, Ljava/io/UnsupportedEncodingException;->printStackTrace()V

    goto :goto_2
.end method

.method protected onCreate(Landroid/os/Bundle;)V
    .locals 1
    .param p1, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    .line 26
    invoke-super {p0, p1}, Landroid/support/v7/app/AppCompatActivity;->onCreate(Landroid/os/Bundle;)V

    .line 27
    const v0, 0x7f040019

    invoke-virtual {p0, v0}, Lcom/ph0en1x/android_crackme/MainActivity;->setContentView(I)V

    .line 29
    const v0, 0x7f0c004f

    invoke-virtual {p0, v0}, Lcom/ph0en1x/android_crackme/MainActivity;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/widget/EditText;

    iput-object v0, p0, Lcom/ph0en1x/android_crackme/MainActivity;->etFlag:Landroid/widget/EditText;

    .line 30
    return-void
.end method

.method public onGoClick(Landroid/view/View;)V
    .locals 4
    .param p1, "v"    # Landroid/view/View;

    .prologue
    const/4 v3, 0x1

    .line 33
    iget-object v1, p0, Lcom/ph0en1x/android_crackme/MainActivity;->etFlag:Landroid/widget/EditText;

    invoke-virtual {v1}, Landroid/widget/EditText;->getText()Landroid/text/Editable;

    move-result-object v1

    invoke-virtual {v1}, Ljava/lang/Object;->toString()Ljava/lang/String;

    move-result-object v0

    .line 34
    .local v0, "sInput":Ljava/lang/String;
    invoke-virtual {p0}, Lcom/ph0en1x/android_crackme/MainActivity;->getFlag()Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p0, v1}, Lcom/ph0en1x/android_crackme/MainActivity;->getSecret(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-virtual {p0, v0}, Lcom/ph0en1x/android_crackme/MainActivity;->encrypt(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {p0, v2}, Lcom/ph0en1x/android_crackme/MainActivity;->getSecret(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v2

    invoke-virtual {v1, v2}, Ljava/lang/String;->equals(Ljava/lang/Object;)Z

    move-result v1

    if-eqz v1, :cond_0

    .line 35
    const-string v1, "Success"

    invoke-static {p0, v1, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    .line 39
    :goto_0
    return-void

    .line 37
    :cond_0
    const-string v1, "Failed"

    invoke-static {p0, v1, v3}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v1

    invoke-virtual {v1}, Landroid/widget/Toast;->show()V

    goto :goto_0
.end method
