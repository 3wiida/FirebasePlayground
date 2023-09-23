package com.mahmoudibrahem.firebaseplayground.ui.screens.cloud_storage

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mahmoudibrahem.firebaseplayground.R
import com.mahmoudibrahem.firebaseplayground.ui.theme.SfDisplay

@Composable
fun CloudStorageScreen(
    viewModel: CloudStorageViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {
    val uiState by viewModel.uiState.collectAsState()
    CloudStorageContent(
        uiState = uiState,
        onImageNameChanged = viewModel::onImageNameChanged,
        updateUri = viewModel::updateUri,
        onUploadButtonClicked = { viewModel.onUploadButtonClicked(context) },
        onShowImagesClicked = viewModel::onShowImagesClicked
    )
    if (uiState.showImagesBottomSheet) {
        ImagesBottomSheet(
            onDismiss = viewModel::onBottomSheetDismiss,
            imagesList = uiState.imagesList
        )
    }
    LaunchedEffect(key1 = uiState.errorMsg) {
        if (uiState.errorMsg.isNotEmpty())
            Toast.makeText(context, uiState.errorMsg, Toast.LENGTH_LONG).show()
    }
    LaunchedEffect(key1 = uiState.successMsg) {
        if (uiState.successMsg.isNotEmpty())
            Toast.makeText(context, uiState.successMsg, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun CloudStorageContent(
    uiState: CloudStorageUIState,
    onImageNameChanged: (String) -> Unit,
    updateUri: (Uri?) -> Unit,
    onUploadButtonClicked: () -> Unit,
    onShowImagesClicked: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(state = rememberScrollState())
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            SelectImageSection(
                imageName = uiState.caption,
                imageUri = uiState.imageUri,
                onImageNameChanged = onImageNameChanged,
                updateUri = updateUri
            )
        }
        UploadSection(
            modifier = Modifier.align(Alignment.BottomCenter),
            uploadProgress = uiState.uploadProgress,
            isLoading = uiState.isButtonLoading,
            onUploadButtonClicked = onUploadButtonClicked,
            onShowImagesClicked = onShowImagesClicked
        )
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.cloud_storage),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
        Text(
            text = stringResource(R.string.cloud_storage_des),
            fontFamily = SfDisplay,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp),
        )
    }
}

@Composable
fun SelectImageSection(
    imageName: String,
    imageUri: Uri?,
    onImageNameChanged: (String) -> Unit,
    updateUri: (Uri?) -> Unit
) {
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = updateUri
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = imageName,
            onValueChange = onImageNameChanged,
            placeholder = { Text(text = stringResource(R.string.add_caption)) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        ) {
            Text(text = stringResource(R.string.selectimage))
        }
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            model = imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun UploadSection(
    modifier: Modifier = Modifier,
    uploadProgress: Float,
    isLoading: Boolean,
    onUploadButtonClicked: () -> Unit,
    onShowImagesClicked: (Int) -> Unit
) {
    val progress = animateFloatAsState(
        targetValue = uploadProgress,
        label = ""
    )
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LinearProgressIndicator(
            progress = progress.value,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.5f
                )
            ),
            enabled = !isLoading,
            onClick = onUploadButtonClicked,
        ) {
            if (isLoading) {
                CircularProgressIndicator()
            }else{
                Text(text = "Upload")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ClickableText(
            text = AnnotatedString(stringResource(R.string.show_all_images)),
            onClick = onShowImagesClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesBottomSheet(
    onDismiss: () -> Unit,
    imagesList: List<Uri?>
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {
        ImagesBottomSheetContent(imagesList = imagesList)
    }
}

@Composable
fun ImagesBottomSheetContent(imagesList: List<Uri?>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = imagesList) { uri ->
            AsyncImage(
                model = uri, contentDescription = null, contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CloudStorageScreenPreview() {
    CloudStorageScreen()
}