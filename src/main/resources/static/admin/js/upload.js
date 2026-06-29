document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".image-uploader").forEach(initImageUploader);
});

function initImageUploader(uploader) {
    const uploadDir = uploader.dataset.uploadDir;

    const input = document.getElementById(uploader.dataset.inputId);
    const fileInput = document.getElementById(uploader.dataset.fileId);
    const preview = document.getElementById(uploader.dataset.previewId);
    const placeholder = document.getElementById(uploader.dataset.placeholderId);
    const resetButton = document.getElementById(uploader.dataset.resetId);
    const dropzone = uploader.querySelector(".image-uploader__dropzone");
    const loading = uploader.querySelector(".image-uploader__loading");

    if (!input || !fileInput || !preview || !placeholder || !dropzone) {
        return;
    }

    const originalValue = input.dataset.originalValue || "";

    if (input.value) {
        showImage(preview, placeholder, input.value);
        uploader.classList.add("is-uploaded");
    } else {
        showEmpty(preview, placeholder);
    }

    dropzone.addEventListener("click", () => {
        fileInput.click();
    });

    fileInput.addEventListener("change", async () => {
        if (!fileInput.files || fileInput.files.length === 0) {
            return;
        }

        await handleFileUpload({
            file: fileInput.files[0],
            uploadDir,
            input,
            preview,
            placeholder,
            loading,
            uploader
        });
    });

    dropzone.addEventListener("dragover", (event) => {
        event.preventDefault();
        uploader.classList.add("is-dragover");
    });

    dropzone.addEventListener("dragleave", () => {
        uploader.classList.remove("is-dragover");
    });

    dropzone.addEventListener("drop", async (event) => {
        event.preventDefault();
        uploader.classList.remove("is-dragover");

        const file = event.dataTransfer.files?.[0];
        if (!file) {
            return;
        }

        await handleFileUpload({
            file,
            uploadDir,
            input,
            preview,
            placeholder,
            loading,
            uploader
        });
    });

    if (resetButton) {
        resetButton.addEventListener("click", () => {
            fileInput.value = "";
            input.value = originalValue;

            uploader.classList.remove("is-error");

            if (originalValue) {
                showImage(preview, placeholder, originalValue);
                uploader.classList.add("is-uploaded");
            } else {
                showEmpty(preview, placeholder);
                uploader.classList.remove("is-uploaded");
            }
        });
    }
}

async function handleFileUpload({
                                    file,
                                    uploadDir,
                                    input,
                                    preview,
                                    placeholder,
                                    loading,
                                    uploader
                                }) {
    if (!file.type || !file.type.startsWith("image/")) {
        alert("이미지 파일만 업로드할 수 있습니다.");
        return;
    }

    const maxSize = 20 * 1024 * 1024;
    if (file.size > maxSize) {
        alert("이미지는 20MB 이하만 업로드할 수 있습니다.");
        return;
    }

    uploader.classList.remove("is-error");

    const localPreviewUrl = URL.createObjectURL(file);
    showImage(preview, placeholder, localPreviewUrl);

    try {
        setLoading(loading, true);

        const uploadedUrl = await uploadImageToS3(file, uploadDir);

        input.value = uploadedUrl;
        showImage(preview, placeholder, uploadedUrl);

        uploader.classList.add("is-uploaded");

    } catch (error) {
        console.error(error);

        uploader.classList.add("is-error");
        alert("이미지 업로드에 실패했습니다.");

        if (input.value) {
            showImage(preview, placeholder, input.value);
        } else {
            showEmpty(preview, placeholder);
            uploader.classList.remove("is-uploaded");
        }

    } finally {
        setLoading(loading, false);
        URL.revokeObjectURL(localPreviewUrl);
    }
}

async function uploadImageToS3(file, dir) {
    const formData = new FormData();
    formData.append("file", file);
    formData.append("dir", dir);

    const csrfHeader = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content");
    const csrfToken = document.querySelector("meta[name='_csrf']")?.getAttribute("content");

    const headers = {};
    if (csrfHeader && csrfToken) {
        headers[csrfHeader] = csrfToken;
    }

    const response = await fetch("/admin/uploads/image", {
        method: "POST",
        headers,
        body: formData
    });

    if (!response.ok) {
        throw new Error(`Upload failed: ${response.status}`);
    }

    const result = await response.json();

    if (!result.url) {
        throw new Error("Upload response does not contain url.");
    }

    return result.url;
}

function showImage(preview, placeholder, imageUrl) {
    preview.src = imageUrl;
    preview.classList.remove("d-none");
    placeholder.classList.add("d-none");
}

function showEmpty(preview, placeholder) {
    preview.removeAttribute("src");
    preview.classList.add("d-none");
    placeholder.classList.remove("d-none");
}

function setLoading(loading, isLoading) {
    if (!loading) {
        return;
    }

    if (isLoading) {
        loading.classList.remove("d-none");
    } else {
        loading.classList.add("d-none");
    }
}