# CloudFormation

- Intrinsic Function - http://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference.html
  -Fn::Base64
  -Condition Functions (Fn::And, Fn::Equals, Fn::If, Fn::Not, Fn::Or)
  -Fn::FindInMap
  -Fn::GetAtt
  -Fn::GetAZs
  -Fn::ImportValue
  -Fn::Join
  -Fn::Select
  -Fn::Split
  -Fn::Sub
  -Ref

- Pseudo parameters - http://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/pseudo-parameter-reference.html

Currently, AWS CloudFormation provides the following helpers:

- cfn-init: Used to retrieve and interpret the resource metadata, installing packages, creating files and starting services.
- cfn-signal: A simple wrapper to signal an AWS CloudFormation CreationPolicy or WaitCondition, enabling you to synchronize other resources in the stack with the application being ready.
- cfn-get-metadata: A wrapper script making it easy to retrieve either all metadata defined for a resource or path to a specific key or subtree of the resource metadata.
- cfn-hup: A daemon to check for updates to metadata and execute custom hooks when the changes are detected.
