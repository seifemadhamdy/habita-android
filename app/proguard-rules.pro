# Keep the 'Signature' attribute to preserve generic type information.
# This is necessary for libraries or code that use reflection and rely on generic types.
# Removing this attribute could cause issues with runtime operations that depend on it.
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package seifemadhamdy.habita.domain.models.
-keepclassmembers class seifemadhamdy.habita.domain.models.** {
  *;
}