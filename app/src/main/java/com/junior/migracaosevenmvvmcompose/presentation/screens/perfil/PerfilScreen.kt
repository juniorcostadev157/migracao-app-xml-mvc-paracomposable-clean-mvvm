package com.junior.migracaosevenmvvmcompose.presentation.screens.perfil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.junior.migracaosevenmvvmcompose.R
import com.junior.migracaosevenmvvmcompose.presentation.components.AppTopBar
import com.junior.migracaosevenmvvmcompose.presentation.theme.Black
import com.junior.migracaosevenmvvmcompose.presentation.theme.DarkGrey
import com.junior.migracaosevenmvvmcompose.presentation.theme.Red
import com.junior.migracaosevenmvvmcompose.presentation.theme.White

@Composable
fun PerfilScreen (
    onMenuClick: () -> Unit
){
   Scaffold(
       topBar = {
           AppTopBar(
               title = "Perfil",
               onMenuClick = onMenuClick,
           )
       },
       containerColor = White
   ){padding->
       Column (
           modifier = Modifier.fillMaxSize().padding(padding).background(White),
           horizontalAlignment = Alignment.CenterHorizontally
       ){
           Spacer(modifier = Modifier.height(24.dp))
           Image(
               painter = painterResource(id = R.drawable.fotoperfil),
               contentDescription = "Foto do perfil",
               modifier = Modifier.size(160.dp).clip(CircleShape)
           )

           HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
           Spacer(modifier = Modifier.height(16.dp))

           Text(
               text = "Junior",
               fontSize = 16.sp,
               color = DarkGrey
           )

           Spacer(modifier = Modifier.height(16.dp))

           Text(
               text = "email@email.com",
               fontSize = 16.sp,
               color = DarkGrey
           )

           Spacer(modifier = Modifier.height(16.dp))

           Text(
               text = "Z183519",
               fontSize = 16.sp,
               color = DarkGrey
           )
           Spacer(modifier = Modifier.height(16.dp))


           Text(
               text = "Dashboard",
               fontSize = 16.sp,
               color = DarkGrey
           )

           Spacer(modifier = Modifier.height(16.dp))

           Row(
               modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
               horizontalArrangement = Arrangement.SpaceEvenly
           ) {
                DashboardItem(1, "INICIALIZADOS")
                DashboardItem(2, "SUSPEITOS")
                DashboardItem(3, "INSTAL. DO MÊS")
                DashboardItem(4, "INICIALIZADOS")

           }

           Spacer(modifier = Modifier.height(48.dp))

           Button(
               onClick = {},
               modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
               colors = ButtonDefaults.buttonColors(containerColor = Red),
               contentPadding = PaddingValues(vertical = 14.dp),
               shape = MaterialTheme.shapes.medium
           ) {
               Text(
                   text = "POLÍTICA DE PRIVACIDADE",
                   fontSize = 16.sp,
                   color = White,
                   fontWeight = FontWeight.Bold
               )
           }


       }

   }
}

@Composable
fun DashboardItem(value: Int, label: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(60.dp).clip(CircleShape)
            .background(DarkGrey), contentAlignment = Alignment.Center){
            Text(
                text = value.toString(),
                fontSize = 18.sp,
                color = White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                fontSize = 12.sp,
                color = Black
            )
        }
    }
    
}
@Preview(showBackground = true)
@Composable
fun PreviewPerfilScreen(){
    PerfilScreen(onMenuClick = {})
}