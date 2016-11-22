
node {
 def flavorCombination='Prod'

 stage 'checkout'
  checkout scm

 stage 'UITest'
  lock('adb') {
   try {
    sh "./gradlew clean spoon${flavorCombination}"
   } catch(err) {
    currentBuild.result = FAILURE
   } finally {
    publishHTML(target:[allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: "android/build/spoon", reportFiles: '*/debug/index.html', reportName: 'Spoon'])
    step([$class: 'JUnitResultArchiver', testResults: 'android/build/spoon/*/debug/junit-reports/*.xml'])   
   }
  }


 stage 'lint'
  try {
   sh "./gradlew lint${flavorCombination}Release"
  } catch(err) {
   currentBuild.result = FAILURE
  } finally {
   androidLint canComputeNew: false, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
  }
  
 stage 'assemble'
  sh "./gradlew assemble${flavorCombination}Release"
  archive 'android/build/outputs/apk/*'
  archive 'android/build/outputs/mapping/*/release/mapping.txt'
     
}