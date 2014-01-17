/*******************************************************************************
* Copyright 2014 Jeff MAURY
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
******************************************************************************/
package org.gololang.gldt.jdt.internal.aether;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResolutionException;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.resolution.VersionRequest;
import org.eclipse.aether.resolution.VersionResult;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.version.Version;
import org.gololang.gldt.jdt.internal.GoloJdtPlugin;

/**
 * @author jeffmaury
 *
 */
public class AetherHelper {
  public static AetherHelper INSTANCE = new AetherHelper();
  private File goloFile;
  
  private AetherHelper() {}
  
  protected DefaultRepositorySystemSession newRepositorySystemSession( RepositorySystem system )
  {
      DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

      LocalRepository localRepo = new LocalRepository( GoloJdtPlugin.getDefault().getStateLocation().append("repo").toFile().getAbsolutePath() );
      session.setLocalRepositoryManager( system.newLocalRepositoryManager( session, localRepo ) );
      return session;
  }

  protected List<RemoteRepository> newRepositories( RepositorySystem system, RepositorySystemSession session )
  {
      return new ArrayList<RemoteRepository>( Arrays.asList( newSnapshotsRepository() ) );
  }

  protected RemoteRepository newSnapshotsRepository()
  {
      return new RemoteRepository.Builder( "snapshots", "default", "https://oss.sonatype.org/content/repositories/snapshots/" ).build();
  }

  protected RepositorySystem newRepositorySystem()
  {
      /*
       * Aether's components implement org.eclipse.aether.spi.locator.Service to ease manual wiring and using the
       * prepopulated DefaultServiceLocator, we only need to register the repository connector and transporter
       * factories.
       */
      DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
      locator.addService( RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class );
      locator.addService( TransporterFactory.class, HttpTransporterFactory.class );
  
      locator.setErrorHandler( new DefaultServiceLocator.ErrorHandler()
      {
          @Override
          public void serviceCreationFailed( Class<?> type, Class<?> impl, Throwable exception )
          {
              exception.printStackTrace();
          }
      } );
  
      return locator.getService( RepositorySystem.class );
  }
  
  public File resolveLatestGoloJarFile() throws IOException {
    try {
      if (goloFile == null) {
        RepositorySystem system = newRepositorySystem();

        RepositorySystemSession session = newRepositorySystemSession( system );

        Artifact artifact = new DefaultArtifact( "org.golo-lang:golo:[0,)" );

        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact( artifact );
        rangeRequest.setRepositories(newRepositories( system, session ) );

        VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest );

        List<Version> versions = rangeResult.getVersions();

        System.out.println( "Available versions " + versions );
        System.out.println("Latest version " + rangeResult.getHighestVersion());
        
        artifact = new DefaultArtifact( "org.golo-lang:golo:" + rangeResult.getHighestVersion().toString());
        
        ArtifactRequest artifactRequest = new ArtifactRequest();
        artifactRequest.setArtifact( artifact );
        artifactRequest.setRepositories(newRepositories( system, session ) );

        ArtifactResult artifactResult = system.resolveArtifact( session, artifactRequest );

        artifact = artifactResult.getArtifact();

        System.out.println( artifact + " resolved to  " + artifact.getFile() );
         goloFile = artifact.getFile();
      }
      return goloFile;
    }
    catch (ArtifactResolutionException e) {
      throw new IOException(e);
    }
    catch (VersionRangeResolutionException e) {
      throw new IOException(e);
    }
  }
}
