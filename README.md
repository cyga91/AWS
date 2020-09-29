# package
sam package \
   --template-file template.yaml \
   --output-template-file serverless-output.yaml \
   --s3-bucket <YOUR-BUCKETNAME>

# deploy
sam deploy \
   --template-file serverless-output.yaml \
   --stack-name <YOUR-ENV-NAME> \
   --capabilities CAPABILITY_IAM
   --region eu-west-1