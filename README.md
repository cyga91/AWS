# create bucket
aws s3 mb s3://BUCKETNAME

# package
sam package \
   --template-file template.yaml \
   --output-template-file serverless-output.yaml \
   --s3-bucket <YOUR-BUCKETNAME>

# deploy
sam deploy \
   --template-file serverless-output.yaml \
   --stack-name <STACK-NAME> \
   --capabilities CAPABILITY_IAM
   --region eu-west-1