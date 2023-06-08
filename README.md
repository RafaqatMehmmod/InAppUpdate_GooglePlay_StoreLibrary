# InAppUpdate_GooglePlay_StoreLibrary
<img width="434" alt="play_modal" src="https://user-images.githubusercontent.com/107309494/190345336-ed54a68f-7eb9-4a49-b928-5ba1150c00ea.png">

# Step 1. Add the JitPack repository to your build file
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  # Step 2. Add the dependency
  dependencies {
	        implementation 'com.github.RafaqatMehmmod:InAppUpdate_GooglePlay_StoreLibrary:Tag'
	}
