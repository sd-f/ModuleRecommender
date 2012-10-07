/**
 * 
 */
package com.co.server.guice.requestfactory.inject;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.RequestContext;

/**
 * InjectingServiceLayerDecorator is a ServiceLayerDecorator that uses DI to inject
 * service, entities and the JSR 303 validator.
 */
public class InjectingServiceLayerDecorator extends ServiceLayerDecorator {

  private final Injector injector;

  /**
   * Creates new InjectableServiceLayer.
   */
  @Inject
  public InjectingServiceLayerDecorator(Injector injector) {
    this.injector = injector;
  }

  /**
   * Uses Guice to create the instance of the target locator, so the locator implementation could be injected.
   */
  @Override
  public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
    return injector.getInstance(clazz);
  }

  @Override
  public Object createServiceInstance(Class<? extends RequestContext> requestContext) {
    Class<?> serviceClazz = resolveServiceClass(requestContext);
    return injector.getInstance(serviceClazz);
  }



}
